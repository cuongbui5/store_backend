package com.example.storeback.model.pk;

import com.example.storeback.model.Address;
import com.example.storeback.model.Product;
import com.example.storeback.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserAddressId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAddressId that = (UserAddressId) o;
        return Objects.equals(address, that.address) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, user);
    }
}
