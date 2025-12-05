package com.rsb.Asset.Management.System.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rsb.Asset.Management.System.models.enums.ApprovalDecision;
//import com.rsb.Asset.Management.System.models.enums.ApprovalRole;
import com.rsb.Asset.Management.System.models.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "request_approvals")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RequestApproval {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approval_id")
    private Integer approvalId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "approval_role", nullable = false)
    private UserRole approvalRole;
    
    @CreationTimestamp
    @Column(name = "approval_date", nullable = false, updatable = false)
    private LocalDateTime approvalDate;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "decision", nullable = false)
    private ApprovalDecision decision;
    
    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private AssetRequest request;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by", nullable = false)
    private User approvedBy;
    
    public RequestApproval() {}
    
    // Getters and Setters
    public Integer getApprovalId() {
        return approvalId;
    }
    
    public void setApprovalId(Integer approvalId) {
        this.approvalId = approvalId;
    }
    
    public UserRole getApprovalRole() {
        return approvalRole;
    }
    
    public void setApprovalRole(UserRole approvalRole) {
        this.approvalRole = approvalRole;
    }
    
    public LocalDateTime getApprovalDate() {
        return approvalDate;
    }
    
    public void setApprovalDate(LocalDateTime approvalDate) {
        this.approvalDate = approvalDate;
    }
    
    public ApprovalDecision getDecision() {
        return decision;
    }
    
    public void setDecision(ApprovalDecision decision) {
        this.decision = decision;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public AssetRequest getRequest() {
        return request;
    }
    
    public void setRequest(AssetRequest request) {
        this.request = request;
    }
    
    public User getApprovedBy() {
        return approvedBy;
    }
    
    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }
}
