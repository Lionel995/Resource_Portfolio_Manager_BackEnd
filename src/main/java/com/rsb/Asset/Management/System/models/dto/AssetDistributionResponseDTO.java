package com.rsb.Asset.Management.System.models.dto;

public class AssetDistributionResponseDTO {
    private Integer id;
    private Integer requestId;       // ✅ add requestId
    private String assetName;
    private String distributedTo;
    private String distributedDate;
    private String status;
    private String returnedDate;
    private String returnedBy;

    // ✅ constructor including requestId
    public AssetDistributionResponseDTO(Integer id, Integer requestId, String assetName,
                                        String distributedTo, String distributedDate,
                                        String status, String returnedDate, String returnedBy) {
        this.id = id;
        this.requestId = requestId;
        this.assetName = assetName;
        this.distributedTo = distributedTo;
        this.distributedDate = distributedDate;
        this.status = status;
        this.returnedDate = returnedDate;
        this.returnedBy = returnedBy;
    }

    public AssetDistributionResponseDTO() {}

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getRequestId() { return requestId; }
    public void setRequestId(Integer requestId) { this.requestId = requestId; }

    public String getAssetName() { return assetName; }
    public void setAssetName(String assetName) { this.assetName = assetName; }

    public String getDistributedTo() { return distributedTo; }
    public void setDistributedTo(String distributedTo) { this.distributedTo = distributedTo; }

    public String getDistributedDate() { return distributedDate; }
    public void setDistributedDate(String distributedDate) { this.distributedDate = distributedDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getReturnedDate() { return returnedDate; }
    public void setReturnedDate(String returnedDate) { this.returnedDate = returnedDate; }

    public String getReturnedBy() { return returnedBy; }
    public void setReturnedBy(String returnedBy) { this.returnedBy = returnedBy; }
}
