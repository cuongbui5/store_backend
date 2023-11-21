package com.example.storeback.validator;


import com.example.storeback.dto.request.RegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterRequest> {
    @Override
    public boolean isValid(RegisterRequest value, ConstraintValidatorContext context) {
        return value.isPasswordConfirmed();
    }
}
