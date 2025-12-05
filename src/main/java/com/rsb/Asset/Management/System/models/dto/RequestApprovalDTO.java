package com.rsb.Asset.Management.System.models.dto;

import com.rsb.Asset.Management.System.models.enums.ApprovalDecision;
import java.time.LocalDateTime;

public class RequestApprovalDTO {
    private Integer id;
    private Integer requestId;
    private String approvedBy;
    private ApprovalDecision decision;
    private String remarks;
    private LocalDateTime approvalDate;

    public RequestApprovalDTO(Integer id, Integer requestId, String approvedBy,
                              ApprovalDecision decision, String remarks, LocalDateTime approvalDate) {
        this.id = id;
        this.requestId = requestId;
        this.approvedBy = approvedBy;
        this.decision = decision;
        this.remarks = remarks;
        this.approvalDate = approvalDate;
    }

    // ✅ Getters
    public Integer getId() { return id; }
    public Integer getRequestId() { return requestId; }
    public String getApprovedBy() { return approvedBy; }
    public ApprovalDecision getDecision() { return decision; }
    public String getRemarks() { return remarks; }
    public LocalDateTime getApprovalDate() { return approvalDate; }
}
