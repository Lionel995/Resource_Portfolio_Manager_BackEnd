package com.rsb.Asset.Management.System.service;

import com.rsb.Asset.Management.System.models.entity.AssetType;
import com.rsb.Asset.Management.System.repository.AssetTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetTypeService {
    @Autowired
    private AssetTypeRepository assetTypeRepository;

    public List<AssetType> getAllTypes() {
        return assetTypeRepository.findAll();
    }

    public AssetType getTypeById(Integer id) {
        return assetTypeRepository.findById(id).orElse(null);
    }

    public AssetType createType(AssetType type) {
        return assetTypeRepository.save(type);
    }

    public AssetType updateType(Integer id, AssetType type) {
        AssetType existing = assetTypeRepository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setTypeName(type.getTypeName());
        existing.setCategory(type.getCategory());
        return assetTypeRepository.save(existing);
    }

    public void deleteType(Integer id) {
        assetTypeRepository.deleteById(id);
    }
}