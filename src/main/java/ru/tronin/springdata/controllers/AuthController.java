package ru.tronin.springdata.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tronin.springdata.config.jwt.JwtProvider;
import ru.tronin.springdata.models.dto.AuthRequestDto;
import ru.tronin.springdata.models.dto.AuthResponseDto;
import ru.tronin.springdata.models.dto.SignUpRequestDto;
import ru.tronin.springdata.models.entities.users.User;
import ru.tronin.springdata.services.UserService;

@RestController
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/signup")
    public void registerUser(@RequestBody SignUpRequestDto signUpRequest) {
        User user = new User();
        user.setPassword(signUpRequest.getPassword());
        user.setLogin(signUpRequest.getLogin());
        userService.saveUser(user);
    }

    @PostMapping("/login")
    public AuthResponseDto auth(@RequestBody AuthRequestDto request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponseDto(token);
    }
}
