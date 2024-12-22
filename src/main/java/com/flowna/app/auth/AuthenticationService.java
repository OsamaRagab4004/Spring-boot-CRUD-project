package com.flowna.app.auth;

import com.flowna.app.config.JwtService;
import com.flowna.app.email.EmailService;
import com.flowna.app.token.Token;
import com.flowna.app.token.TokenRepository;
import com.flowna.app.token.TokenType;
import com.flowna.app.user.Role;
import com.flowna.app.user.User;
import com.flowna.app.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  @Autowired
  private final TwoFACodeService twoFACodeService;
  @Autowired
  private final EmailService emailService;



  public ResponseEntity<?> register(RegisterRequest request) {
    String TwoFAcode = twoFACodeService.generate2FACode();
    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .message2FA(TwoFAcode)
            /**
             * need to be false in production
             * **/
        .verified(true)
        .build();

    var savedUser = repository.save(user);
    Map<String, Object> variables = new HashMap<>();
    variables.put("name", request.getFirstname() + " " + request.getLastname());
    variables.put("token", TwoFAcode);
    variables.put("confirmUrl","http://localhost:5173/id?" + TwoFAcode);
    emailService.sendSimpleEmail(request.getEmail(),
            "Confirmation Code",
            "emailTemplate.html",variables
            );
    return ResponseEntity.ok("Confirm your Email !");
  }

  public ResponseEntity<?> authenticate(AuthenticationRequest request) {

    // if the user doesn't exist in DB, it throws error
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    boolean UserIsVerified = user.isVerified();
    System.out.println(UserIsVerified);
    if (!UserIsVerified) {
      System.out.println("Not Verified user !");
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
              .body("verification failed ! Please verify your email");
    }

    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    var response = AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
    return ResponseEntity.ok(response);
  }

  public boolean VerifyUserCode(AuthenticationResponseTwoFA auth) {
    System.out.println(auth);
    var user = repository.findByEmail(auth.email)
            .orElseThrow();
    String DB_Code = user.getMessage2FA();
    System.out.println("DB : " + DB_Code);
    System.out.println("Param: " + auth.getTwoFactor());
    if(DB_Code.equals(auth.getTwoFactor())) {
        user.setVerified(true);
        user.setMessage2FA(null);
        repository.save(user);
        return true;
    }
    return false;
  }



  public ResponseEntity<?> googleAuthenticateUser(Map<String, String> payload) {
    String idTokenString = payload.get("token");
    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
            .setAudience(Collections.singletonList("429779320499-mua8d18h0qcel99fb2v682jteih7fo5m.apps.googleusercontent.com"))
            .build();

    try {
      GoogleIdToken idToken = verifier.verify(idTokenString);
      if (idToken != null) {
        GoogleIdToken.Payload idTokenPayload = idToken.getPayload();
        String userId = idTokenPayload.getSubject();
        String email = idTokenPayload.getEmail();
        boolean emailVerified = Boolean.valueOf(idTokenPayload.getEmailVerified());
        String name = (String) idTokenPayload.get("name");
        Optional<User> user = repository.findByEmail(email);
        if(user.isPresent()) {
          // if user is existed and authenticate with google
          var authenticated_existed_user = user.orElseThrow(() -> new UsernameNotFoundException("User not found"));
          var jwtToken = jwtService.generateToken(authenticated_existed_user);
          var refreshToken = jwtService.generateRefreshToken(authenticated_existed_user);
          revokeAllUserTokens(authenticated_existed_user);
          saveUserToken(authenticated_existed_user, jwtToken);
          var response =  AuthenticationResponse.builder()
                  .accessToken(jwtToken)
                  .refreshToken(refreshToken)
                  .build();
          return ResponseEntity.ok(response);

        } else {
          // if the user is new and authenticate with google
          var googleAuthenticatedUser = User.builder()
                  .firstname(name)
                  .lastname("")
                  .email(email)
                  .password(passwordEncoder.encode(idTokenString))
                  .role(Role.ADMIN)
                  .verified(true)
                  .build();
          var savedUser = repository.save(googleAuthenticatedUser);
          var jwtToken = jwtService.generateToken(googleAuthenticatedUser);
          var refreshToken = jwtService.generateRefreshToken(googleAuthenticatedUser);
          saveUserToken(savedUser, jwtToken);
          var response = AuthenticationResponse.builder()
                  .accessToken(jwtToken)
                  .refreshToken(refreshToken)
                  .build();
          return ResponseEntity.ok(response);
        }
      }
    } catch (GeneralSecurityException | IOException e) {
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token verification failed");

    }

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token verification failed");

  }


  public ResponseEntity<?> requestResetPassword(ResetPasswordRequest resetPasswordRequest) {
    Optional<User> user = repository.findByEmail(resetPasswordRequest.getEmail());
    var user_of_requested_password = user.orElseThrow();
    if(user.isPresent() && user_of_requested_password.isVerified()) {
      String currentTimeInSeconds = String.valueOf(twoFACodeService.getCurrentTimeSeconds());
      final String resetCode = twoFACodeService.generate2FACode()  + currentTimeInSeconds ;
      user_of_requested_password.setResetPassword(resetCode);
      repository.save(user_of_requested_password);
      Map<String, Object> variables = new HashMap<>();
      variables.put("name", user_of_requested_password.getFirstname() + " " + user_of_requested_password.getLastname());
      variables.put("token", resetCode);
      variables.put("confirmUrl","http://localhost:5173/id=?" + resetCode+"&email=?" + user_of_requested_password.getEmail());
      emailService.sendSimpleEmail(user_of_requested_password.getEmail(),
              "Reset your passowrd",
              "emailTemplate",
              variables);
      return ResponseEntity.ok("Code is sent !");
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not found !");

  }

  public ResponseEntity<?> resetPassword(ResetPasswordRequest resetPasswordRequest) {
    Optional<User> user = repository.findByEmail(resetPasswordRequest.getEmail());
    var requesterUser = user.orElseThrow();
    final String currentResetCode = requesterUser.getResetPassword();
    if(user.isPresent() && requesterUser.getResetPassword() != null && currentResetCode.equals(resetPasswordRequest.getResetCode())) {
      final long secondsSinceRequest = twoFACodeService.CalculateDifference(resetPasswordRequest.getResetCode());
      if(secondsSinceRequest > 1800) return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body("Timeout! please generate new Request !");
      String password = resetPasswordRequest.getPassword();
      requesterUser.setPassword(passwordEncoder.encode(password));
      requesterUser.setResetPassword(null);
      repository.save(requesterUser);
      return ResponseEntity.ok("Password changed !");
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found or link is wrong !");

  }


  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        /*Summary:

    ObjectMapper: Converts Java objects to JSON and vice versa.
    writeValue(): Serializes the authResponse object to JSON and writes it to the provided OutputStream.
    response.getOutputStream(): Provides an OutputStream for writing data (in this case, JSON) to the HTTP response body.
    Purpose: This line sends the authResponse object (containing authentication information) back to the client in JSON format as part of the HTTP response.*/
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
