package com.rsb.Asset.Management.System.repository;

import com.rsb.Asset.Management.System.models.entity.AssetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Integer> {
}
