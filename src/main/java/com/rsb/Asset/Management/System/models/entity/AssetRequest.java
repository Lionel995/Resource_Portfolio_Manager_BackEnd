package com.rsb.Asset.Management.System.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rsb.Asset.Management.System.models.enums.RequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "asset_requests")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AssetRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Integer requestId;
    
    @CreationTimestamp
    @Column(name = "request_date", nullable = false, updatable = false)
    private LocalDateTime requestDate;
    
    @Min(1)
    @Column(name = "quantity", nullable = false)
    private Integer quantity = 1;
    
    @NotBlank
    @Column(name = "reason", columnDefinition = "TEXT", nullable = false)
    private String reason;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "current_status", nullable = false)
    private RequestStatus currentStatus = RequestStatus.PENDING;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_by", nullable = false)
    private User requestedBy;
    
    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RequestApproval> requestApprovals;
    
    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AssetDistribution> assetDistributions;
    
    public AssetRequest() {}
    
    // Getters and Setters
    public Integer getRequestId() {
        return requestId;
    }
    
    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }
    
    public LocalDateTime getRequestDate() {
        return requestDate;
    }
    
    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public RequestStatus getCurrentStatus() {
        return currentStatus;
    }
    
    public void setCurrentStatus(RequestStatus currentStatus) {
        this.currentStatus = currentStatus;
    }
    
    public Asset getAsset() {
        return asset;
    }
    
    public void setAsset(Asset asset) {
        this.asset = asset;
    }
    
    public User getRequestedBy() {
        return requestedBy;
    }
    
    public void setRequestedBy(User requestedBy) {
        this.requestedBy = requestedBy;
    }
    
    public List<RequestApproval> getRequestApprovals() {
        return requestApprovals;
    }
    
    public void setRequestApprovals(List<RequestApproval> requestApprovals) {
        this.requestApprovals = requestApprovals;
    }
    
    public List<AssetDistribution> getAssetDistributions() {
        return assetDistributions;
    }
    
    public void setAssetDistributions(List<AssetDistribution> assetDistributions) {
        this.assetDistributions = assetDistributions;
    }
}
