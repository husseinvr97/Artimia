package Artimia.com.annotations.ConstraintAnnotations.ConstraintAnnotationsValidiators;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import Artimia.com.annotations.ConstraintAnnotations.StrongPassword;
import Artimia.com.enums.PasswordStrength;
import Artimia.com.exceptions.WhiteSpaceException;
import Artimia.com.services.UserServices;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@Component
public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> 
{

    @Override
    public boolean isValid(String passwordPlain, ConstraintValidatorContext context) 
    {
        try 
        {
            final PasswordStrength passwordStrength = UserServices.passwordStrength(passwordPlain);
            if(passwordStrength.equals(PasswordStrength.WEAK) || passwordStrength.equals(PasswordStrength.BELOW_AVERAGE))
                return false;
        } catch (WhiteSpaceException e) 
        {
            return false;
        }
        return true;
    }
    @ExceptionHandler(ConstraintViolationException.class)
public ResponseEntity<Map<String, String>> handleValidationErrors(
    ConstraintViolationException ex
) {
    Map<String, String> errors = ex.getConstraintViolations()
        .stream()
        .collect(Collectors.toMap(
            v -> v.getPropertyPath().toString(),
            ConstraintViolation::getMessage
        ));
        
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
}
}
