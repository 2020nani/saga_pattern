package br.com.userservice.application.controller;

import br.com.userservice.application.dto.UserRequest;
import br.com.userservice.core.User.UserService;
import br.com.userservice.core.document.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public User createUser(@Valid @RequestBody UserRequest userRequest){
        return userService.createUser(userRequest);
    }
}
