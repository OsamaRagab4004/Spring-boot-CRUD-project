package com.flowna.app.config;

import com.flowna.app.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

  private final TokenRepository tokenRepository;

  @Override
  public void logout(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication
  ) {
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    jwt = authHeader.substring(7);
    var storedToken = tokenRepository.findByToken(jwt)
        .orElse(null);
    if (storedToken != null) {
      storedToken.setExpired(true);
      storedToken.setRevoked(true);
      tokenRepository.save(storedToken);
      SecurityContextHolder.clearContext();
    }
  }
}
/*
* When You Should Store JWTs in a Database:

    Token Revocation (Blacklist):
        Revoking Tokens: If you need the ability to revoke tokens before they expire (e.g., during logout or if a token is compromised), you would need to store the token (or its signature) in a blacklist in the database.
        For example, when a user logs out, you can mark their token as revoked in the database, preventing further use of that token even if it hasn’t expired.

    Refresh Tokens:
        Refresh Tokens: Unlike access tokens, refresh tokens are often long-lived (e.g., valid for days or weeks). It’s common to store refresh tokens in a database to track their validity and allow the server to revoke them if necessary.
* */