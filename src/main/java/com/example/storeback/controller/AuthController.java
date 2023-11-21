package com.example.storeback.controller;


import com.example.storeback.config.jwt.JwtService;
import com.example.storeback.dto.request.LoginRequest;
import com.example.storeback.dto.request.RefreshTokenRequest;
import com.example.storeback.dto.request.RegisterRequest;
import com.example.storeback.dto.response.BaseResponse;
import com.example.storeback.dto.response.LoginResponse;
import com.example.storeback.dto.response.ResponseData;
import com.example.storeback.dto.response.UserResponse;
import com.example.storeback.service.impl.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {

        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(authService.register(registerRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
        return ResponseEntity.ok()
                .body(authService.login(loginRequest));
    }
    @PostMapping("/token")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest refreshToken) {
        System.out.println(refreshToken);

        return ResponseEntity.ok()
                .body(authService.refreshToken(refreshToken.getRefreshToken()));
    }




}
