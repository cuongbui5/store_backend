package com.example.storeback.service;

import com.example.storeback.model.RefreshToken;
import com.example.storeback.model.User;

public interface IRefreshTokenService {
    RefreshToken createRefreshToken(Long userId);
    void deleteRefreshTokenByUserId(Long userId);
    RefreshToken getRefreshTokenByToken(String token);
    boolean verifyExpiration(RefreshToken token) ;
    boolean checkExistByUserId(Long userId);


}
