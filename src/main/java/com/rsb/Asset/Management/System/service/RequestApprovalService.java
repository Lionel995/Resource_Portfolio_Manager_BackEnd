package com.rsb.Asset.Management.System.service;

import com.rsb.Asset.Management.System.models.entity.AssetRequest;
import com.rsb.Asset.Management.System.models.entity.RequestApproval;
import com.rsb.Asset.Management.System.models.entity.User;
import com.rsb.Asset.Management.System.models.enums.ApprovalDecision;
import com.rsb.Asset.Management.System.models.enums.RequestStatus;
import com.rsb.Asset.Management.System.models.enums.UserRole;
import com.rsb.Asset.Management.System.repository.AssetRequestRepository;
import com.rsb.Asset.Management.System.repository.RequestApprovalRepository;
import com.rsb.Asset.Management.System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestApprovalService {

    @Autowired
    private RequestApprovalRepository requestApprovalRepository;

    @Autowired
    private AssetRequestRepository assetRequestRepository;

    @Autowired
    private UserRepository userRepository;

    public RequestApproval approveOrReject(Integer requestId, String username, ApprovalDecision decision, String remarks) {
        User approver = userRepository.findByEmail(username).orElseThrow();
        AssetRequest request = assetRequestRepository.findById(requestId).orElseThrow();

        RequestApproval approval = new RequestApproval();
        approval.setRequest(request);
        approval.setApprovedBy(approver);
        approval.setApprovalRole(approver.getRole());
        approval.setDecision(decision);
        approval.setRemarks(remarks);

        // workflow rules
        if (approver.getRole() == UserRole.DIVISION_MANAGER) {
            if (decision == ApprovalDecision.APPROVED) {
                request.setCurrentStatus(RequestStatus.ACCEPTED_BY_MANAGER);
            } else {
                request.setCurrentStatus(RequestStatus.DENIED_BY_MANAGER);
            }
        } else if (approver.getRole() == UserRole.DIRECTOR_DAF) {
            if (decision == ApprovalDecision.APPROVED) {
                request.setCurrentStatus(RequestStatus.APPROVED_BY_DAF);
            } else {
                request.setCurrentStatus(RequestStatus.REJECTED_BY_DAF);
            }
        } else {
            throw new RuntimeException("User not allowed to approve this request");
        }

        assetRequestRepository.save(request);
        return requestApprovalRepository.save(approval);
    }
}
