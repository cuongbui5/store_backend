package com.example.storeback.service;


import com.example.storeback.dto.request.LoginRequest;
import com.example.storeback.dto.request.RegisterRequest;
import com.example.storeback.dto.response.BaseResponse;
import com.example.storeback.dto.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthService {
    BaseResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);

    LoginResponse refreshToken(String token);
}
