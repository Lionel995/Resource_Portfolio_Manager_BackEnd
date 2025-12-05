package com.rsb.Asset.Management.System.models.dto;

public class AssetDistributionRequestDTO {
    private Integer assetId;
    private Integer requestId;
    private Integer distributedToId;
    private String distributedDate;
    public Integer getAssetId() {
        return assetId;
    }
    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }
    public Integer getRequestId() {
        return requestId;
    }
    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }
    public Integer getDistributedToId() {
        return distributedToId;
    }
    public void setDistributedToId(Integer distributedToId) {
        this.distributedToId = distributedToId;
    }
    public String getDistributedDate() {
        return distributedDate;
    }
    public void setDistributedDate(String distributedDate) {
        this.distributedDate = distributedDate;
    }
    public AssetDistributionRequestDTO(Integer assetId, Integer requestId, Integer distributedToId, String distributedDate) {
        this.assetId = assetId;
        this.requestId = requestId;
        this.distributedToId = distributedToId;
        this.distributedDate = distributedDate;
    } 

    public AssetDistributionRequestDTO() {}
}
