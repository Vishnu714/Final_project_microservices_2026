package com.example.order.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shipments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String trackingNumber;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
