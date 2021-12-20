package com.nsu.camcam.controller;

import com.nsu.camcam.controller.requests.AuthRequest;
import com.nsu.camcam.controller.response.AuthResponse;
import com.nsu.camcam.controller.response.OperationResponse;
import com.nsu.camcam.model.UserCredential;
import com.nsu.camcam.provider.JwtProvider;
import com.nsu.camcam.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {
    public static final String USER_ALREADY_REGISTER = "Пользователь с таким данными уже зарегестрирован";
    public static final String USER_CREDENTIALS_INVALID = "Данные пользователя не валидны";

    @Autowired
    private CredentialsService userService;
    @Autowired
    private JwtProvider jwtProvider;


    @PostMapping("/register")
    public OperationResponse registerUser(@RequestBody @Valid AuthRequest authRequest) {
        UserCredential userCredential = new UserCredential(
                authRequest.getUsername(),
                authRequest.getPassword()
        );

        if (userService.saveCredentials(userCredential)) {
            return new OperationResponse();
        }

        return new OperationResponse(false, USER_ALREADY_REGISTER);
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        UserCredential userCredential = userService.findByUsernameAndPassword(request.getUsername(), request.getPassword());

        if (userCredential == null) {
            return new AuthResponse(null, USER_CREDENTIALS_INVALID);
        }

        String token = jwtProvider.createJWTToken(userCredential);
        return new AuthResponse(token, null);
    }

}