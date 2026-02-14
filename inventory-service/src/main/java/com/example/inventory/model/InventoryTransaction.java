package com.example.inventory.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventory_transactions")
@Getter
@Setter
@NoArgsConstructor
public class InventoryTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Integer changeQuantity; // + or -

    private String reason; // RESTOCK, ORDER, MANUAL

}
