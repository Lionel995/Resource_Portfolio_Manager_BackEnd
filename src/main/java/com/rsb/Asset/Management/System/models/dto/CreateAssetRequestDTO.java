package com.rsb.Asset.Management.System.models.dto;

public class CreateAssetRequestDTO {
    private Integer assetId;
    private Integer quantity;
    private String reason;
    // getters and setters
    public Integer getAssetId() {
        return assetId;
    }
    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
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
    public CreateAssetRequestDTO(Integer assetId, Integer quantity, String reason) {
        this.assetId = assetId;
        this.quantity = quantity;
        this.reason = reason;
    }

    

}

