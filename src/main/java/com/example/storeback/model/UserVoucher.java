package com.example.storeback.model;

import com.example.storeback.model.pk.UserAddressId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_voucher")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserVoucher {
    @Id
    private UserAddressId id;
    private Boolean used;

}
