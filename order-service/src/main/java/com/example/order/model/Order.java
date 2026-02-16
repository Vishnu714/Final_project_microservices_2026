package com.example.order.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private OrderStatus status;

    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<OrderItem> items;

    @OneToOne(mappedBy = "order",
            cascade = CascadeType.ALL)
    private Shipment shipment;
}
