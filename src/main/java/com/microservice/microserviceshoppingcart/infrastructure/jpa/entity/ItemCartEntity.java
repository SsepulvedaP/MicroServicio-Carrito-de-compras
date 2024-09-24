package com.microservice.microserviceshoppingcart.infrastructure.jpa.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "item_cart")
public class ItemCartEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "product_id", nullable = false)
        private Long productId;

        @Column(nullable = false)
        private int quantity;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "shopping_cart_id", nullable = false)
        @JsonIgnore
        private ShoppingCartEntity shoppingCartEntity;

}
