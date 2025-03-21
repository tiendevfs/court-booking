package com.court.booking.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MatchPasswordValidator.class)
@Documented
public @interface MatchPassword {
    String message() default "Mật khẩu không khớp";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
