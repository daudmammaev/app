package app.payload.request;

import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignupRequest {
  @NotBlank
  private String username;

  @NotBlank
  @Size(max = 50)
  private String email;

  private Set<String> role;

  @NotBlank
  private String password;

}
