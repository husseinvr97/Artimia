package Artimia.com.annotations.ConstraintAnnotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import Artimia.com.annotations.ConstraintAnnotations.ConstraintAnnotationsValidiators.StrongPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword 
{
    String message() default "Password is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}