package com.example.order.audit;

import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private final AuditLogRepository repo;

    public AuditService(AuditLogRepository repo) {
        this.repo = repo;
    }

    public void log(String entity, Long id, String action, Long userId) {

        repo.save(AuditLog.builder()
                .entityName(entity)
                .entityId(id)
                .action(action)
                .changedBy(userId)
                .build());
    }
}
