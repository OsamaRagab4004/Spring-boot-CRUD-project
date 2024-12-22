package com.flowna.app.config;

import com.flowna.app.auditing.ApplicationAuditAware;
import com.flowna.app.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  private final UserRepository repository;

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> repository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  /*
  * How It Works in the Authentication Process:

    Login Attempt:
        When a user attempts to log in with a username and password, Spring Security will use the AuthenticationProvider to process the authentication request.

    UserDetailsService:
        The DaoAuthenticationProvider will call the UserDetailsService (set via setUserDetailsService()) to load the user by their username or email from the database.
        The UserDetailsService will return a UserDetails object that contains the user's information (username, password, roles, etc.).

    Password Verification:
        Once the UserDetails object is loaded, the DaoAuthenticationProvider will use the PasswordEncoder (set via setPasswordEncoder()) to verify the provided password against the encoded password stored in the database.

    Authentication Success or Failure:
        If the password matches, the user is authenticated and Spring Security proceeds with the request, storing the authentication details in the SecurityContext.
        If the password doesn’t match, authentication fails, and the user is not allowed to access the protected resource.
  * */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuditorAware<Integer> auditorAware() {
    return new ApplicationAuditAware();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }



  // Define a custom CORS configuration


}
