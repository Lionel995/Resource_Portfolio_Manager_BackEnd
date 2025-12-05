package com.rsb.Asset.Management.System.controller;

import com.rsb.Asset.Management.System.models.dto.AssetRequestDTO;
import com.rsb.Asset.Management.System.models.dto.CreateAssetRequestDTO;
import com.rsb.Asset.Management.System.models.entity.AssetRequest;
import com.rsb.Asset.Management.System.models.enums.RequestStatus;
import com.rsb.Asset.Management.System.service.AssetRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.rsb.Asset.Management.System.models.entity.User;
import com.rsb.Asset.Management.System.repository.UserRepository;


import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/requests")
public class AssetRequestController {

    @Autowired
    private AssetRequestService assetRequestService;

    @Autowired
private UserRepository userRepository;


    @GetMapping("/my")
    @PreAuthorize("hasRole('STAFF')")
    public List<AssetRequest> getMyRequests(Principal principal) {
        return assetRequestService.getRequestsForUser(principal.getName());
    }

    @GetMapping("/division")
@PreAuthorize("hasRole('DIVISION_MANAGER')")
public List<AssetRequestDTO> getDivisionRequests(Principal principal) {
    // Fetch the logged-in manager
    User manager = userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));

    String divisionName = manager.getDivision().getDivisionName(); // ✅ fixed
    List<AssetRequest> requests = assetRequestService.getRequestsForDivision(divisionName);

    // Map to DTOs
    return requests.stream()
            .map(r -> new AssetRequestDTO(
                    r.getRequestId(),
                    r.getRequestDate(),
                    r.getQuantity(),
                    r.getReason(),
                    r.getCurrentStatus().name(),
                    r.getAsset().getAssetId(),
                    r.getAsset().getAssetName(),
                    r.getRequestedBy().getUserId(),
                    r.getRequestedBy().getFullName()
            ))
            .toList();
}




    @GetMapping("/all")
@PreAuthorize("hasAnyRole('DIRECTOR_DAF','DG','LOGISTIC', 'ADMIN', 'DIVISION_MANAGER')")
public List<AssetRequestDTO> getAllRequests() {
    return assetRequestService.getAllRequestsDTO();
}


    @PostMapping("/create")
@PreAuthorize("hasAnyRole('STAFF','DIVISION_MANAGER','DIRECTOR_DAF','DG','LOGISTIC','ADMIN')")
public AssetRequestDTO createRequest(@RequestBody CreateAssetRequestDTO dto, Principal principal) {
    return assetRequestService.createRequest(dto, principal.getName());
}


    @PutMapping("/accept/{id}")
    @PreAuthorize("hasRole('DIVISION_MANAGER')")
    public AssetRequest acceptRequest(@PathVariable Integer id) {
        return assetRequestService.updateRequestStatus(id, RequestStatus.ACCEPTED_BY_MANAGER);
    }

    @PutMapping("/deny/{id}")
    @PreAuthorize("hasRole('DIVISION_MANAGER')")
    public AssetRequest denyRequest(@PathVariable Integer id) {
        return assetRequestService.updateRequestStatus(id, RequestStatus.DENIED_BY_MANAGER);
    }

    @PutMapping("/approve/{id}")
    @PreAuthorize("hasRole('DIRECTOR_DAF')")
    public AssetRequest approveRequest(@PathVariable Integer id) {
        return assetRequestService.updateRequestStatus(id, RequestStatus.APPROVED_BY_DAF);
    }

    @PutMapping("/reject/{id}")
    @PreAuthorize("hasRole('DIRECTOR_DAF')")
    public AssetRequest rejectRequest(@PathVariable Integer id) {
        return assetRequestService.updateRequestStatus(id, RequestStatus.REJECTED_BY_DAF);
    }
}