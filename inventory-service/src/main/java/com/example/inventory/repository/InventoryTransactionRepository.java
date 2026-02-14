package com.example.inventory.repository;

import com.example.inventory.model.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryTransactionRepository
        extends JpaRepository<InventoryTransaction, Long> {
}
