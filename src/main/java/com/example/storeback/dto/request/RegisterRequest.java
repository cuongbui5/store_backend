package com.example.storeback.dto.request;


import com.example.storeback.validator.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@PasswordMatches
public class RegisterRequest {
    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotEmpty(message = "Password confirmation is required")
    private String passwordConfirm;
    public boolean isPasswordConfirmed() {
        return password.equals(passwordConfirm);
    }
}
