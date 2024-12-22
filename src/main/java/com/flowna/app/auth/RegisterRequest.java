package com.flowna.app.auth;

import com.flowna.app.user.Role;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstname;
  private String lastname;

  @Email(message = "Invalid email address")
  @NotBlank(message = "Email is required")
  private String email;

  @NotBlank(message = "Password is required")
  @Size(min = 8, message = "Password must be at least 8 characters long")
  @Pattern(
          regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
          message = "Password must contain at least one digit, one uppercase letter, one lowercase letter, and one special character (@#$%^&+=)"
  )
  @Column(nullable = true)
  private String password;
  private Role role;
}
