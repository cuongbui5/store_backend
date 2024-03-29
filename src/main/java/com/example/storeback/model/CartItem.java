package com.example.storeback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItem extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "product_id",referencedColumnName = "id")
  private Product product;
  @ManyToOne
  @JoinColumn(name = "cart_id",referencedColumnName = "id")
  private Cart cart;
  private Integer quantity;
  private Double totalPrice;


}
