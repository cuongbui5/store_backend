package com.example.storeback.repository;


import com.example.storeback.model.RefreshToken;
import com.example.storeback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    RefreshToken findRefreshTokenByToken(String token);
    @Modifying
    @Query("delete from RefreshToken rt where rt.user.id = :userId")
    void deleteRefreshTokenByUserId(@Param("userId") Long userId);


    boolean existsByUserId(Long userId);
}
