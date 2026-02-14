package com.example.inventory.audit;

import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private final AuditLogRepository repository;

    public AuditService(AuditLogRepository repository) {
        this.repository = repository;
    }

    public void log(String entityName, Long entityId, String action, Long changedBy) {

        AuditLog log = new AuditLog();
        log.setEntityName(entityName);
        log.setEntityId(entityId);
        log.setAction(action);
        log.setChangedBy(changedBy);

        repository.save(log);
    }
}
