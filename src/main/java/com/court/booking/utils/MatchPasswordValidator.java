package com.court.booking.utils;

import com.court.booking.Model.DTO.LoginRequest;
import com.court.booking.Model.DTO.RegisterRequest;
import com.court.booking.Model.DTO.UserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.util.Objects;

public class MatchPasswordValidator implements ConstraintValidator<MatchPassword, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        String password = "";
        String confirmPassword = "";
        if(o instanceof RegisterRequest){
            RegisterRequest oValidator = (RegisterRequest) o;
            password = oValidator.getPassword();
            confirmPassword = oValidator.getConfirmPassword();
        }else{
            UserRequest oValidator = (UserRequest) o;
            password = oValidator.getPassword();
            confirmPassword = oValidator.getConfirmPassword();
        }


        if(!password.equals(confirmPassword)){
            context.buildConstraintViolationWithTemplate("Mật khẩu không khớp")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();;
            return false;
        }
        return true;
    }

    @Override
    public void initialize(MatchPassword constraintAnnotation) {

    }
}
