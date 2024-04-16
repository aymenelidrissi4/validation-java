package com.example.validation.validator;

import com.example.validation.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Locale;

@Component
public class UserValidator implements Validator {
    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", messageSource.getMessage("NotEmpty.user.username", null, Locale.getDefault()));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", messageSource.getMessage("NotEmpty.user.email", null, Locale.getDefault()));

        if (user.getUsername().length() < 5) {
            errors.rejectValue("username", messageSource.getMessage("Size.userForm.username", null, Locale.getDefault()));
        }
    }
}