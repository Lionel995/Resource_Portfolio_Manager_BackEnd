package com.rsb.Asset.Management.System.controller;

import com.rsb.Asset.Management.System.models.dto.RequestApprovalDTO;
import com.rsb.Asset.Management.System.models.entity.RequestApproval;
import com.rsb.Asset.Management.System.models.enums.ApprovalDecision;
import com.rsb.Asset.Management.System.service.RequestApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/approvals")
public class RequestApprovalController {

    @Autowired
    private RequestApprovalService requestApprovalService;

    @PostMapping("/{requestId}")
    @PreAuthorize("hasAnyRole('DIVISION_MANAGER','DIRECTOR_DAF')")
    public RequestApprovalDTO handleApproval(
            @PathVariable Integer requestId,
            @RequestParam ApprovalDecision decision,
            @RequestParam(required = false) String remarks,
            Principal principal
    ) {
        RequestApproval approval = requestApprovalService
                .approveOrReject(requestId, principal.getName(), decision, remarks);

        Integer reqId = null;
        if (approval.getRequest() != null) {
            reqId = approval.getRequest().getRequestId();
        }

        return new RequestApprovalDTO(
                approval.getApprovalId(),
                reqId,
                approval.getApprovedBy().getEmail(),
                approval.getDecision(),
                approval.getRemarks(),
                approval.getApprovalDate()
        );
    }
}
