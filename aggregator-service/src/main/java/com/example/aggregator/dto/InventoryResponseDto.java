package com.example.aggregator.dto;

public class InventoryResponseDto {

    private Long id;
    private Long productId;
    private Integer quantity;

    public InventoryResponseDto() {}

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
