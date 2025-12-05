package com.rsb.Asset.Management.System.service;

import com.rsb.Asset.Management.System.models.entity.AssetCategory;
import com.rsb.Asset.Management.System.repository.AssetCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetCategoryService {
    @Autowired
    private AssetCategoryRepository assetCategoryRepository;

    public List<AssetCategory> getAllCategories() {
        return assetCategoryRepository.findAll();
    }

    public AssetCategory getCategoryById(Integer id) {
        return assetCategoryRepository.findById(id).orElse(null);
    }

    public AssetCategory createCategory(AssetCategory category) {
        return assetCategoryRepository.save(category);
    }

    public AssetCategory updateCategory(Integer id, AssetCategory category) {
        AssetCategory existing = assetCategoryRepository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setCategoryName(category.getCategoryName());
        return assetCategoryRepository.save(existing);
    }

    public void deleteCategory(Integer id) {
        assetCategoryRepository.deleteById(id);
    }
}