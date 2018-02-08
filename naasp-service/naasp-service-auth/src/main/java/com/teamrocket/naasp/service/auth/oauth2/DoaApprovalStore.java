package com.teamrocket.naasp.service.auth.oauth2;

import com.teamrocket.naasp.service.auth.oauth2.doa.AuthApprovalDoa;
import com.teamrocket.naasp.service.auth.oauth2.model.AuthApproval;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * DOA implementation of an approval store.
 */
@Component
public class DoaApprovalStore implements ApprovalStore {
    private final AuthApprovalDoa authApprovalDoa;
    private boolean handleRevocationsAsExpiry = false;

    @Autowired
    public DoaApprovalStore(AuthApprovalDoa authApprovalDoa) {
        this.authApprovalDoa = authApprovalDoa;
    }

    @Override
    public boolean addApprovals(Collection<Approval> approvals) {
        final Collection<AuthApproval> authApprovals = toAuthApprovals(approvals);
        authApprovalDoa.updateOrCreate(authApprovals);
        return true;
    }

    @Override
    public boolean revokeApprovals(Collection<Approval> approvals) {
        Collection<AuthApproval> mongoApprovals = toAuthApprovals(approvals);

        for (AuthApproval authApproval : mongoApprovals) {
            if (handleRevocationsAsExpiry) {
                authApproval.setExpiresAt(LocalDate.now());
                authApprovalDoa.update(authApproval);
            } else {
               authApprovalDoa.delete(authApproval.getId());
            }
        }

        return true;
    }

    @Override
    public Collection<Approval> getApprovals(final String userId,
                                             final String clientId) {
        Collection<AuthApproval> authApprovals = authApprovalDoa.findByUserIdAndClientId(userId, clientId);
        return toApprovals(authApprovals);
    }

    private List<AuthApproval> toAuthApprovals (Collection <Approval> approvals) {
        List<AuthApproval> authApprovals = new ArrayList<>();

        for (Approval approval : approvals) {
            authApprovals.add(toAuthApproval(approval));
        }

        return authApprovals;
    }

    private AuthApproval toAuthApproval (Approval approval) {
        return new AuthApproval(
                UUID.randomUUID().toString(),
                approval.getUserId(),
                approval.getClientId(),
                approval.getScope(),
                approval.getStatus() == null ? Approval.ApprovalStatus.APPROVED: approval.getStatus(),
                LocalDate.fromDateFields(approval.getExpiresAt()),
                LocalDate.fromDateFields(approval.getLastUpdatedAt()));
    }

    private List<Approval> toApprovals (Collection <AuthApproval> authApprovals) {
        List<Approval> approvals = new ArrayList<>();

        for (AuthApproval authApproval : authApprovals) {
            approvals.add(toApproval(authApproval));
        }

        return approvals;
    }

    private Approval toApproval (AuthApproval authApproval) {
        return new Approval(authApproval.getUserId(),
                authApproval.getClientId(),
                authApproval.getScope(),
                authApproval.getExpiresAt().toDate(),
                authApproval.getStatus(),
                authApproval.getLastUpdatedAt().toDate());
    }

    public void setHandleRevocationsAsExpiry(boolean handleRevocationsAsExpiry) {
        this.handleRevocationsAsExpiry = handleRevocationsAsExpiry;
    }
}