package com.rsb.Asset.Management.System.models.entity;

//import com.rsb.Asset.Management.System.models.enums.DistributionStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.rsb.Asset.Management.System.models.enums.DistributionStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "asset_distribution")
public class AssetDistribution {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "distribution_id")
    private Integer distributionId;
    
    @CreationTimestamp
    @Column(name = "distribution_date", nullable = false, updatable = false)
    private LocalDateTime distributionDate;
    
    @Column(name = "returned_date")
    private LocalDateTime returnedDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DistributionStatus status = DistributionStatus.IN_USE;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private AssetRequest request;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distributed_to", nullable = false)
    private User distributedTo;

    @Column(name = "returned_by")
    private String returnedBy;

    
    public AssetDistribution() {}
    
    // Getters and Setters
    public Integer getDistributionId() {
        return distributionId;
    }
    
    public void setDistributionId(Integer distributionId) {
        this.distributionId = distributionId;
    }
    
    public LocalDateTime getDistributionDate() {
        return distributionDate;
    }
    
    public void setDistributionDate(LocalDateTime distributionDate) {
        this.distributionDate = distributionDate;
    }
    
    public LocalDateTime getReturnedDate() {
        return returnedDate;
    }
    
    public void setReturnedDate(LocalDateTime returnedDate) {
        this.returnedDate = returnedDate;
    }
    
    public DistributionStatus getStatus() {
        return status;
    }
    
    public void setStatus(DistributionStatus status) {
        this.status = status;
    }
    
    public Asset getAsset() {
        return asset;
    }
    
    public void setAsset(Asset asset) {
        this.asset = asset;
    }
    
    public AssetRequest getRequest() {
        return request;
    }
    
    public void setRequest(AssetRequest request) {
        this.request = request;
    }
    
    public User getDistributedTo() {
        return distributedTo;
    }
    
    public void setDistributedTo(User distributedTo) {
        this.distributedTo = distributedTo;
    }

    public String getReturnedBy() {
    return returnedBy;
}

public void setReturnedBy(String returnedBy) {
    this.returnedBy = returnedBy;
}
}
