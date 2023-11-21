package com.example.storeback.model;

import com.example.storeback.model.pk.UserAddressId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserAddress extends BaseEntity{
    @Id
    private UserAddressId id;
    private Boolean isDefault;
}
