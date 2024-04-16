package com.example.validation.controller;

import com.example.validation.model.User;
import com.example.validation.validator.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserValidator userValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody User user, BindingResult bindingResult) throws URISyntaxException {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream().findAny().get().getCode();
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.created(new URI("/users")).body(messageSource.getMessage("username.created", null, Locale.getDefault()));
    }
}