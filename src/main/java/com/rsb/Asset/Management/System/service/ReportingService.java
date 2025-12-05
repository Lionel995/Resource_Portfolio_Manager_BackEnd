package com.rsb.Asset.Management.System.service;

import com.rsb.Asset.Management.System.models.entity.*;
import com.rsb.Asset.Management.System.models.enums.*;
import com.rsb.Asset.Management.System.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportingService {
    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetRequestRepository assetRequestRepository;

    @Autowired
    private UserRepository userRepository;

    // STAFF: Only their own requested assets
    public List<AssetRequest> getStaffRequestedAssets(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return List.of();
        return assetRequestRepository.findByRequestedByUserId(user.getUserId());
    }

    // DIVISION MANAGER: Own requests, all requests in division, accepted/denied by manager
    public List<AssetRequest> getDivisionManagerRequestedAssets(String username) {
        User manager = userRepository.findByUsername(username).orElse(null);
        if (manager == null || manager.getDivision() == null) return List.of();
        Integer divisionId = manager.getDivision().getDivisionId();
        // All requests by users in this division
        return assetRequestRepository.findAll().stream()
            .filter(req -> req.getRequestedBy() != null && req.getRequestedBy().getDivision() != null
                && req.getRequestedBy().getDivision().getDivisionId().equals(divisionId))
            .collect(Collectors.toList());
    }

    public List<AssetRequest> getDivisionManagerOwnRequests(String username) {
        return getStaffRequestedAssets(username);
    }

    public List<AssetRequest> getDivisionManagerAcceptedOrDenied(String username) {
        User manager = userRepository.findByUsername(username).orElse(null);
        if (manager == null) return List.of();
        // Requests where currentStatus is ACCEPTED_BY_MANAGER or DENIED_BY_MANAGER and manager is in the same division
        Integer divisionId = manager.getDivision() != null ? manager.getDivision().getDivisionId() : null;
        return assetRequestRepository.findAll().stream()
            .filter(req -> req.getCurrentStatus() == RequestStatus.ACCEPTED_BY_MANAGER
                        || req.getCurrentStatus() == RequestStatus.DENIED_BY_MANAGER)
            .filter(req -> req.getRequestedBy() != null && req.getRequestedBy().getDivision() != null
                && req.getRequestedBy().getDivision().getDivisionId().equals(divisionId))
            .collect(Collectors.toList());
    }

    // DIRECTOR_DAF & DG: Own requests, all requests, approved/rejected by DAF
    public List<AssetRequest> getDirectorRequestedAssets(String username) {
        return getStaffRequestedAssets(username);
    }

    public List<AssetRequest> getAllRequestedAssets() {
        return assetRequestRepository.findAll();
    }

    public List<AssetRequest> getDafApprovedOrRejected() {
        return assetRequestRepository.findAll().stream()
            .filter(req -> req.getCurrentStatus() == RequestStatus.APPROVED_BY_DAF
                        || req.getCurrentStatus() == RequestStatus.REJECTED_BY_DAF)
            .collect(Collectors.toList());
    }

    // LOGISTIC: Own requests, all requests, distributed, instock, all registered assets
    public List<AssetRequest> getLogisticRequestedAssets(String username) {
        return getStaffRequestedAssets(username);
    }

    public List<AssetRequest> getLogisticAllRequestedAssets() {
        return assetRequestRepository.findAll();
    }

    public List<AssetDistribution> getDistributedAssets() {
        // Distributed: returnedDate is null
        return assetRepository.findAll().stream()
            .flatMap(asset -> asset.getAssetDistributions().stream())
            .filter(dist -> dist.getReturnedDate() == null)
            .collect(Collectors.toList());
    }

    public List<Asset> getInstockAssets() {
        // Instock: status AVAILABLE
        return assetRepository.findAll().stream()
            .filter(asset -> asset.getStatus() == AssetStatus.AVAILABLE)
            .collect(Collectors.toList());
    }

    public List<Asset> getAllRegisteredAssets() {
        return assetRepository.findAll();
    }

    // ADMIN: All assets, all requests, archived assets
    public List<Asset> getAdminAllAssets() {
        return assetRepository.findAll();
    }

    public List<AssetRequest> getAdminAllRequests() {
        return assetRequestRepository.findAll();
    }

    public List<Asset> getArchivedAssets() {
        return assetRepository.findAll().stream()
            .filter(asset -> asset.getStatus() == AssetStatus.DISPOSED)
            .collect(Collectors.toList());
    }
}