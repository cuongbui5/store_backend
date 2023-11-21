package com.example.storeback.config.jwt;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.storeback.config.CustomUserDetails;
import com.example.storeback.config.authentication.CustomAbstractAuthenticationToken;
import com.example.storeback.dto.response.BaseResponse;
import com.example.storeback.util.Constant;
import com.example.storeback.util.Helper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtDecoder jwtDecoder;
    private final JwtToCustomUserDetailsConverter jwtToCustomUserDetailsConverter;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if ("/api/v1/auth/login".equals(requestURI) ||
                "/api/v1/auth/register".equals(requestURI)||
                "/api/v1/auth/token".equals(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        try{
            String token = request.getHeader("Authorization").substring(7);
            DecodedJWT jwt=jwtDecoder.decode(token);
            CustomUserDetails userDetails=jwtToCustomUserDetailsConverter.converter(jwt);
            CustomAbstractAuthenticationToken customAbstractAuthenticationToken=new CustomAbstractAuthenticationToken(userDetails);
            SecurityContextHolder.getContext().setAuthentication(customAbstractAuthenticationToken);

        }catch (Exception e){
            BaseResponse res = getBaseResponse(e);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            OutputStream responseStream = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(responseStream, res);
            responseStream.flush();
            return;

        }


        filterChain.doFilter(request,response);
    }

    private static BaseResponse getBaseResponse(Exception e) {
        String message="";
        if (e instanceof JWTDecodeException) {
            message = "JWT doesn't have a valid JSON format";
        } else if (e instanceof SignatureVerificationException) {
            message = "JWT signature verification failed";
        } else if (e instanceof JWTVerificationException) {
            message = "JWT verification failed";
        }
        return new BaseResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()), message.isEmpty() ? e.getMessage():message);
    }


}
