package com.rsb.Asset.Management.System.service;

import com.rsb.Asset.Management.System.models.dto.AssetRequestDTO;
import com.rsb.Asset.Management.System.models.dto.CreateAssetRequestDTO;
import com.rsb.Asset.Management.System.models.entity.Asset;
import com.rsb.Asset.Management.System.models.entity.AssetRequest;
import com.rsb.Asset.Management.System.models.entity.User;
import com.rsb.Asset.Management.System.models.enums.RequestStatus;
import com.rsb.Asset.Management.System.repository.AssetRepository;
import com.rsb.Asset.Management.System.repository.AssetRequestRepository;
import com.rsb.Asset.Management.System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetRequestService {
    @Autowired
    private AssetRequestRepository assetRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssetRepository assetRepository;

    public List<AssetRequest> getRequestsForUser(String email) {
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

    return assetRequestRepository.findByRequestedByUserId(user.getUserId());
}


    public List<AssetRequest> getRequestsForDivision(String divisionName) {
    return assetRequestRepository.findByRequestedBy_Division_DivisionName(divisionName);

}


    public List<AssetRequestDTO> getAllRequestsDTO() {
    List<AssetRequest> requests = assetRequestRepository.findAll();

    return requests.stream()
            .map(r -> new AssetRequestDTO(
                    r.getRequestId(),
                    r.getRequestDate(),
                    r.getQuantity(),
                    r.getReason(),
                    r.getCurrentStatus().name(),
                    r.getAsset().getAssetId(),             // ✅ add assetId
                    r.getAsset().getAssetName(),
                    r.getRequestedBy().getUserId(),        // ✅ add requestedById
                    r.getRequestedBy().getFullName()
            ))
            .toList();
}


    public AssetRequestDTO createRequest(CreateAssetRequestDTO dto, String email) {
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Asset asset = assetRepository.findById(dto.getAssetId())
            .orElseThrow(() -> new RuntimeException("Asset not found"));

    boolean exists = assetRequestRepository.existsByRequestedByAndAssetAndCurrentStatus(
            user, asset, RequestStatus.PENDING);

    if (exists) {
        throw new RuntimeException("You already have a pending request for this asset.");
    }

    AssetRequest request = new AssetRequest();
    request.setRequestedBy(user);
    request.setAsset(asset);
    request.setQuantity(dto.getQuantity());
    request.setReason(dto.getReason());
    request.setCurrentStatus(RequestStatus.PENDING);

    AssetRequest saved = assetRequestRepository.save(request);

    return new AssetRequestDTO(
            saved.getRequestId(),
            saved.getRequestDate(),
            saved.getQuantity(),
            saved.getReason(),
            saved.getCurrentStatus().name(),
            saved.getAsset().getAssetId(),           // ✅ add assetId
            saved.getAsset().getAssetName(),
            saved.getRequestedBy().getUserId(),      // ✅ add requestedById
            saved.getRequestedBy().getFullName()
    );
}






    public AssetRequest updateRequestStatus(Integer requestId, RequestStatus status) {
        AssetRequest request = assetRequestRepository.findById(requestId).orElseThrow();
        request.setCurrentStatus(status);
        return assetRequestRepository.save(request);
    }

    public List<AssetRequest> getRequestsForApproval() {
        return assetRequestRepository.findByCurrentStatus(RequestStatus.ACCEPTED_BY_MANAGER);
    }
}