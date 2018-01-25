package com.teamrocket.naasp.service.auth.oauth2.model;

import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus;

/**
 * A data object that represents and oauth2 sso auth approval.
 */
public class AuthApproval {
    @Id
    private String id;
    private String userId;
    private String clientId;
    private String scope;
    private ApprovalStatus status;
    private LocalDate expiresAt;
    private LocalDate lastUpdatedAt;

    public AuthApproval() {}

    public AuthApproval(String id,
                        String userId,
                        String clientId,
                        String scope,
                        ApprovalStatus status,
                        LocalDate expiresAt,
                        LocalDate lastUpdatedAt) {
        this.id = id;
        this.userId = userId;
        this.clientId = clientId;
        this.scope = scope;
        this.status = status;
        this.expiresAt = expiresAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    public LocalDate getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDate expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDate getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDate lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
