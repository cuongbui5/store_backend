package com.example.storeback.model.pk;


import com.example.storeback.model.User;
import com.example.storeback.model.Voucher;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Embeddable
public class UserVoucherId {
    @ManyToOne
    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
    private Voucher voucher;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVoucherId that = (UserVoucherId) o;
        return Objects.equals(voucher, that.voucher) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucher, user);
    }
}
