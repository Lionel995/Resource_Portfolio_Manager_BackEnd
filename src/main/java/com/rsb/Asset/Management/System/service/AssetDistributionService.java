package com.rsb.Asset.Management.System.service;

import com.rsb.Asset.Management.System.models.dto.AssetDistributionRequestDTO;
import com.rsb.Asset.Management.System.models.dto.AssetDistributionResponseDTO;
import com.rsb.Asset.Management.System.models.entity.Asset;
import com.rsb.Asset.Management.System.models.entity.AssetDistribution;
import com.rsb.Asset.Management.System.models.entity.AssetRequest;
import com.rsb.Asset.Management.System.models.entity.User;
import com.rsb.Asset.Management.System.models.enums.DistributionStatus;
import com.rsb.Asset.Management.System.repository.AssetDistributionRepository;
import com.rsb.Asset.Management.System.repository.AssetRepository;
import com.rsb.Asset.Management.System.repository.AssetRequestRepository;
import com.rsb.Asset.Management.System.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssetDistributionService {

    private final AssetDistributionRepository distributionRepo;
    private final AssetRepository assetRepo;
    private final AssetRequestRepository requestRepo;
    private final UserRepository userRepo;

    public AssetDistributionService(AssetDistributionRepository distributionRepo,
                                    AssetRepository assetRepo,
                                    AssetRequestRepository requestRepo,
                                    UserRepository userRepo) {
        this.distributionRepo = distributionRepo;
        this.assetRepo = assetRepo;
        this.requestRepo = requestRepo;
        this.userRepo = userRepo;
    }

    @Transactional
public AssetDistributionResponseDTO distributeAsset(AssetDistributionRequestDTO dto) {
    Asset asset = assetRepo.findById(dto.getAssetId())
            .orElseThrow(() -> new RuntimeException("Asset not found"));
    AssetRequest request = requestRepo.findById(dto.getRequestId())
            .orElseThrow(() -> new RuntimeException("Request not found"));
    User user = userRepo.findById(dto.getDistributedToId())
            .orElseThrow(() -> new RuntimeException("User not found"));

    // Prevent duplicate distribution for the same request
    boolean alreadyDistributed = distributionRepo.findByRequestRequestId(dto.getRequestId())
            .stream()
            .anyMatch(d -> d.getStatus() == DistributionStatus.IN_USE);

    if (alreadyDistributed) {
        throw new RuntimeException("This request has already been distributed.");
    }

    AssetDistribution distribution = new AssetDistribution();
    distribution.setAsset(asset);
    distribution.setRequest(request);
    distribution.setDistributedTo(user);
    distribution.setStatus(DistributionStatus.IN_USE);
    distribution.setDistributionDate(dto.getDistributedDate() != null && !dto.getDistributedDate().isEmpty()
            ? LocalDateTime.parse(dto.getDistributedDate())
            : LocalDateTime.now());

    // ✅ Update asset
    asset.setStatus(com.rsb.Asset.Management.System.models.enums.AssetStatus.IN_USE);
    asset.setCurrentDivision(user.getDivision()); // 🔹 set current division
    assetRepo.saveAndFlush(asset);

    AssetDistribution saved = distributionRepo.save(distribution);
    return toResponseDTO(saved);
}


@Transactional
public AssetDistributionResponseDTO returnAsset(Integer distributionId, String returnerName) {
    AssetDistribution distribution = distributionRepo.findById(distributionId)
            .orElseThrow(() -> new RuntimeException("Distribution not found"));

    distribution.setStatus(DistributionStatus.RETURNED);
    distribution.setReturnedDate(LocalDateTime.now());
    distribution.setReturnedBy(returnerName);

    Asset asset = distribution.getAsset();
    asset.setStatus(com.rsb.Asset.Management.System.models.enums.AssetStatus.AVAILABLE);
    asset.setCurrentDivision(null); // 🔹 reset current division when returned
    assetRepo.saveAndFlush(asset);

    AssetDistribution saved = distributionRepo.save(distribution);
    return toResponseDTO(saved);
}




    public List<AssetDistributionResponseDTO> getAllDistributions() {
        return distributionRepo.findAll()
                .stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<AssetDistributionResponseDTO> getDistributionById(Integer id) {
        return distributionRepo.findById(id).map(this::toResponseDTO);
    }

    public List<AssetDistributionResponseDTO> getDistributionsByUser(Integer userId) {
        return distributionRepo.findByDistributedToUserId(userId)
                .stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<AssetDistributionResponseDTO> getDistributionsByAsset(Integer assetId) {
        return distributionRepo.findByAssetAssetId(assetId)
                .stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<AssetDistributionResponseDTO> getDistributionsByRequest(Integer requestId) {
        return distributionRepo.findByRequestRequestId(requestId)
                .stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<AssetDistributionResponseDTO> getDistributionsByDateRange(LocalDateTime start, LocalDateTime end) {
        return distributionRepo.findByDistributionDateBetween(start, end)
                .stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<AssetDistributionResponseDTO> getReturnsByDateRange(LocalDateTime start, LocalDateTime end) {
        return distributionRepo.findByReturnDateBetween(start, end)
                .stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private AssetDistributionResponseDTO toResponseDTO(AssetDistribution dist) {
    String assetName = dist.getAsset() != null ? dist.getAsset().getAssetName() : "N/A";
    String distributedTo = dist.getDistributedTo() != null ? dist.getDistributedTo().getFullName() : "N/A";
    Integer requestId = dist.getRequest() != null ? dist.getRequest().getRequestId() : null;

    return new AssetDistributionResponseDTO(
            dist.getDistributionId(),
            requestId, // ✅ include requestId
            assetName,
            distributedTo,
            dist.getDistributionDate() != null ? dist.getDistributionDate().toString() : null,
            dist.getStatus() != null ? dist.getStatus().name() : "IN_USE",
            dist.getReturnedDate() != null ? dist.getReturnedDate().toString() : null,
            dist.getReturnedBy()  // ✅ include returnedBy
    );
}


}
