package com.example.storeback.model;

import com.example.storeback.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "address_id",referencedColumnName = "id")
    private Address address;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;
    @Column(name = "shipping_cost")
    private Double shippingCost;
    private Double total;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "order_voucher",
            joinColumns = @JoinColumn(name = "order_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "voucher_id",
                    referencedColumnName = "id")
    )
    private Set<Voucher> orderVouchers = new HashSet<>();


}
