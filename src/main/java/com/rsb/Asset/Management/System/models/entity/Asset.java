package com.rsb.Asset.Management.System.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rsb.Asset.Management.System.models.enums.AssetStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "assets")
public class Asset {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asset_id")
    private Integer assetId;
    
    @NotBlank
    @Size(max = 100)
    @Column(name = "asset_name", nullable = false)
    private String assetName;
    
    @NotBlank
    @Size(max = 100)
    @Column(name = "asset_number", unique = true, nullable = false)
    private String assetNumber;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @CreationTimestamp
    @Column(name = "registration_date", nullable = false, updatable = false)
    private LocalDateTime registrationDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AssetStatus status = AssetStatus.AVAILABLE;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AssetCategory category;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private AssetType type;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registered_by", nullable = false)
    private User registeredBy;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;
    
    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AssetRequest> assetRequests;
    
    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AssetDistribution> assetDistributions;

    @ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "current_division_id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
private Division currentDivision;
    
    public Asset() {}
    
    // Getters and Setters
    public Integer getAssetId() {
        return assetId;
    }
    
    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }
    
    public String getAssetName() {
        return assetName;
    }
    
    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }
    
    public String getAssetNumber() {
        return assetNumber;
    }
    
    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public AssetStatus getStatus() {
        return status;
    }
    
    public void setStatus(AssetStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public AssetCategory getCategory() {
        return category;
    }
    
    public void setCategory(AssetCategory category) {
        this.category = category;
    }
    
    public AssetType getType() {
        return type;
    }
    
    public void setType(AssetType type) {
        this.type = type;
    }
    
    public User getRegisteredBy() {
        return registeredBy;
    }
    
    public void setRegisteredBy(User registeredBy) {
        this.registeredBy = registeredBy;
    }
    
    public User getUpdatedBy() {
        return updatedBy;
    }
    
    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public List<AssetRequest> getAssetRequests() {
        return assetRequests;
    }
    
    public void setAssetRequests(List<AssetRequest> assetRequests) {
        this.assetRequests = assetRequests;
    }
    
    public List<AssetDistribution> getAssetDistributions() {
        return assetDistributions;
    }
    
    public void setAssetDistributions(List<AssetDistribution> assetDistributions) {
        this.assetDistributions = assetDistributions;
    }

    public Division getCurrentDivision() {
    return currentDivision;
}

public void setCurrentDivision(Division currentDivision) {
    this.currentDivision = currentDivision;
}
}
