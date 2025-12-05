package com.rsb.Asset.Management.System.service;

import com.rsb.Asset.Management.System.models.entity.Asset;
import com.rsb.Asset.Management.System.models.enums.AssetStatus;
import com.rsb.Asset.Management.System.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {
    @Autowired
    private AssetRepository assetRepository;

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Asset registerAsset(Asset asset) {
        asset.setStatus(AssetStatus.AVAILABLE);
        return assetRepository.save(asset);
    }

    public Asset updateAsset(Asset asset) {
        return assetRepository.save(asset);
    }

    public void archiveAsset(Integer assetId) {
        Asset asset = assetRepository.findById(assetId).orElseThrow();
        asset.setStatus(AssetStatus.DISPOSED);
        assetRepository.save(asset);
    }


    public Asset getAssetById(Integer assetId) {
        return assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found with ID: " + assetId));
    }

    // Add more methods as needed for reporting, filtering, etc.
}