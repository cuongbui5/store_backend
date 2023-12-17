package com.example.storeback.service.impl;


import com.example.storeback.config.CustomUserDetails;
import com.example.storeback.config.CustomUserDetailsService;
import com.example.storeback.config.jwt.JwtService;
import com.example.storeback.dto.request.LoginRequest;
import com.example.storeback.dto.request.RegisterRequest;
import com.example.storeback.dto.response.BaseResponse;
import com.example.storeback.dto.response.LoginResponse;
import com.example.storeback.exception.AlreadyExistException;
import com.example.storeback.exception.NotFound;
import com.example.storeback.exception.TokenRefreshException;
import com.example.storeback.exception.WrongPassword;
import com.example.storeback.model.RefreshToken;
import com.example.storeback.model.Role;
import com.example.storeback.model.User;
import com.example.storeback.repository.RoleRepository;
import com.example.storeback.repository.UserRepository;
import com.example.storeback.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;


    @Override
    public BaseResponse register(RegisterRequest registerRequest) {
        if(userRepository.existsUserByEmail(registerRequest.getEmail())){
            throw new AlreadyExistException("Email has been registered!");
        }
        if(userRepository.existsUserByUsername(registerRequest.getUsername())){
            throw new AlreadyExistException("Username has been registered!");
        }

        Optional<Role> role= roleRepository.findRoleByName("USER");
        if(role.isEmpty()){
            throw new NotFound("Not found role : USER");
        }

        Set<Role> roles=new HashSet<>();
        roles.add(role.get());
        User user= User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(user);
        return new BaseResponse("ok","register successful!");
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {

        try {

            Authentication authentication=authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return getLoginResponse(authentication.getName());

        }catch (Exception e){
            if(e instanceof BadCredentialsException){
                throw new WrongPassword("Wrong password!");
            }

            throw new RuntimeException(e.getMessage());
        }


    }

    @Override
    @Transactional
    public LoginResponse refreshToken(String token) {
        RefreshToken refreshToken=refreshTokenService.getRefreshTokenByToken(token);
        if(refreshToken!=null&&refreshTokenService.verifyExpiration(refreshToken)){
            return getLoginResponse(refreshToken.getUser().getUsername());
        }
        throw new TokenRefreshException("Not found token!Please login again!");
    }

    public LoginResponse getLoginResponse(String username) {
        CustomUserDetails customUserDetails= userDetailsService.loadUserByUsername(username);
        if(refreshTokenService.checkExistByUserId(customUserDetails.getUserId())){
            refreshTokenService.deleteRefreshTokenByUserId(customUserDetails.getUserId());
        }
        RefreshToken refreshToken=refreshTokenService.createRefreshToken(customUserDetails.getUserId());
        List<String> roles=customUserDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return LoginResponse.builder()
                .accessToken(jwtService.generateToken(customUserDetails))
                .refreshToken(refreshToken.getToken())
                .userId(customUserDetails.getUserId())
                .username(customUserDetails.getUsername())
                .roles(roles)
                .build();
    }
}
