package com.example.inventory.service;

import com.example.inventory.dto.*;
import com.example.inventory.model.*;
import com.example.inventory.audit.*;
import com.example.inventory.repository.*;
import com.example.inventory.exception.InventoryNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepo;
    private final InventoryTransactionRepository transactionRepo;
    private final AuditLogRepository auditRepo;

    public InventoryServiceImpl(
            InventoryRepository inventoryRepo,
            InventoryTransactionRepository transactionRepo,
            AuditLogRepository auditRepo) {

        this.inventoryRepo = inventoryRepo;
        this.transactionRepo = transactionRepo;
        this.auditRepo = auditRepo;
    }

    @Override
    public InventoryResponseDto getInventory(Long productId) {

        Inventory inventory = inventoryRepo.findByProductId(productId)
                .orElseThrow(() ->
                        new InventoryNotFoundException("Inventory not found"));

        return mapToResponse(inventory);
    }

    @Override
    @Transactional
    public InventoryResponseDto restock(RestockRequestDto request) {

        Inventory inventory = inventoryRepo.findByProductId(request.productId())
                .orElseGet(() -> {
                    Inventory inv = new Inventory();
                    inv.setProductId(request.productId());
                    inv.setQuantity(0);
                    return inv;
                });

        inventory.setQuantity(
                inventory.getQuantity() + request.quantity());

        Inventory saved = inventoryRepo.save(inventory);

        logTransaction(request.productId(),
                       request.quantity(),
                       "RESTOCK");

        logAudit("Inventory",
                 saved.getId(),
                 "UPDATE",
                 1L);

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public InventoryResponseDto reduceStock(ReduceStockRequestDto request) {

        Inventory inventory = inventoryRepo.findByProductId(request.productId())
                .orElseThrow(() ->
                        new InventoryNotFoundException("Inventory not found"));

        if (inventory.getQuantity() < request.quantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        inventory.setQuantity(
                inventory.getQuantity() - request.quantity());

        Inventory saved = inventoryRepo.save(inventory);

        logTransaction(request.productId(),
                       -request.quantity(),
                       "ORDER");

        logAudit("Inventory",
                 saved.getId(),
                 "UPDATE",
                 1L);

        return mapToResponse(saved);
    }

    private void logTransaction(Long productId,
                                Integer change,
                                String reason) {

        InventoryTransaction tx = new InventoryTransaction();
        tx.setProductId(productId);
        tx.setChangeQuantity(change);
        tx.setReason(reason);

        transactionRepo.save(tx);
    }

    private void logAudit(String entity,
                          Long entityId,
                          String action,
                          Long userId) {

        AuditLog log = new AuditLog();
        log.setEntityName(entity);
        log.setEntityId(entityId);
        log.setAction(action);
        log.setChangedBy(userId);

        auditRepo.save(log);
    }

    private InventoryResponseDto mapToResponse(Inventory inv) {
        return new InventoryResponseDto(
                inv.getId(),
                inv.getProductId(),
                inv.getQuantity()
        );
    }
}
