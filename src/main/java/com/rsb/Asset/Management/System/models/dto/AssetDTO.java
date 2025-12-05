package com.rsb.Asset.Management.System.models.dto;

import java.time.LocalDateTime;

import com.rsb.Asset.Management.System.models.entity.Asset;

public class AssetDTO {
    private Integer assetId;
    private String assetName;
    private String assetNumber;
    private String categoryName;
    private String typeName;
    private String status;
    private String registeredBy;
    private LocalDateTime updatedAt;
    private LocalDateTime registrationDate;

    public AssetDTO(Asset asset) {
        this.assetId = asset.getAssetId();
        this.assetName = asset.getAssetName();
        this.assetNumber = asset.getAssetNumber();
        this.categoryName = asset.getCategory().getCategoryName();
        this.typeName = asset.getType().getTypeName();
        this.status = asset.getStatus().name();
        this.registeredBy = asset.getRegisteredBy().getFullName();
        this.updatedAt = asset.getUpdatedAt();
        this.registrationDate = asset.getRegistrationDate();
    }

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(String registeredBy) {
        this.registeredBy = registeredBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    // Getters and Setters
    
}

