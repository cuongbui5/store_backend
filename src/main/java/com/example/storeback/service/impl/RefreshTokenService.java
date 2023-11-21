package com.example.storeback.service.impl;

import com.example.storeback.exception.TokenRefreshException;
import com.example.storeback.model.RefreshToken;
import com.example.storeback.model.User;
import com.example.storeback.repository.RefreshTokenRepository;
import com.example.storeback.repository.UserRepository;
import com.example.storeback.service.IRefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService implements IRefreshTokenService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    @Override
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plus(2, ChronoUnit.DAYS));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public void deleteRefreshTokenByUserId(Long userId) {
        refreshTokenRepository.deleteRefreshTokenByUserId(userId);
    }


    @Override
    public RefreshToken getRefreshTokenByToken(String token) {

        return refreshTokenRepository.findRefreshTokenByToken(token);
    }

    @Override
    public boolean verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new TokenRefreshException("Refresh token was expired. Please login again!");
        }
        return true;
    }

    @Override
    public boolean checkExistByUserId(Long userId) {
        return refreshTokenRepository.existsByUserId(userId);
    }
}
