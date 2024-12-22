package com.flowna.app.auth;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @Operation(summary = "Register a new user", description = "Returns a Http 200ok or 403 if there is an error")
  @PostMapping("/register")
  public ResponseEntity<?> register(
          @Valid  @RequestBody RegisterRequest request
  ) {
    return service.register(request);
  }


  @Operation(summary = "OAuth.2 using google and save user in DB if not exist", description = "Returns a 200ok or internal server error")
  @PostMapping("/google")
  public ResponseEntity<?> auth(
          @RequestBody Map<String, String> payload)
  {
    return service.googleAuthenticateUser(payload);

  }

  @Operation(summary = "Login using email and password.", description = "Returns JWT and refresh token")
  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return service.authenticate(request);
  }

  @Operation(summary = "Verify email using 2FA using email of user.", description = "User gets confirmation email and click on confirm button")
  @PostMapping("/verify")
  public ResponseEntity<?> VerifyUserConfirmationCode(
         @RequestBody AuthenticationResponseTwoFA authTwoFA) {
        System.out.println(authTwoFA + " From API");
        boolean UserVerified = service.VerifyUserCode(authTwoFA);
        if (UserVerified) return ResponseEntity.ok("User is Verfied !");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not verified");
  }

  @Operation(summary = "Make request to change password.", description = "Server sends email to the user with button to change password")
  @PostMapping("/request-reset-password")
  public ResponseEntity<?> requestResetPassword(
          @RequestBody ResetPasswordRequest resetPasswordRequest
  ) {
    return service.requestResetPassword(resetPasswordRequest);
  }

  @Operation(summary = "Update password", description = "User clicks on the button of the sent email and then server gets email, new password, code of request of changing password")
  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(
          @RequestBody ResetPasswordRequest resetPasswordRequest
  ) {
    return service.resetPassword(resetPasswordRequest);
  }


  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }


}
