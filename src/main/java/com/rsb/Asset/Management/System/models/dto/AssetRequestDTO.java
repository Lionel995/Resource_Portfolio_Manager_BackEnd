package com.rsb.Asset.Management.System.models.dto;

import java.time.LocalDateTime;

public class AssetRequestDTO {
    private Integer requestId;
    private LocalDateTime requestDate;
    private Integer quantity;
    private String reason;
    private String currentStatus;
    private Integer assetId;
    private String assetName;
    private Integer requestedById;
    private String requestedByName;
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
    public String getCurrentStatus() {
        return currentStatus;
    }
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
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

    public Integer getRequestedById() {
        return requestedById;
    }
    public void setRequestedById(Integer requestedById) {
        this.requestedById = requestedById;
    }
    public String getRequestedByName() {
        return requestedByName;
    }
    public void setRequestedByName(String requestedByName) {
        this.requestedByName = requestedByName;
    }
    public AssetRequestDTO(Integer requestId,
                       LocalDateTime requestDate,
                       Integer quantity,
                       String reason,
                       String currentStatus,
                       Integer assetId,
                       String assetName,
                       Integer requestedById,
                       String requestedByName) {
    this.requestId = requestId;
    this.requestDate = requestDate;
    this.quantity = quantity;
    this.reason = reason;
    this.currentStatus = currentStatus;
    this.assetId = assetId;                 
    this.assetName = assetName;
    this.requestedById = requestedById;     
    this.requestedByName = requestedByName;
}

    
    
}