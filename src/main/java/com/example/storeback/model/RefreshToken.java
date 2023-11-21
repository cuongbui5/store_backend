package com.example.storeback.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "refresh_token", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "token"})
})
public class RefreshToken extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Instant expiryDate;
    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id", nullable = false)
    private User user;
}
