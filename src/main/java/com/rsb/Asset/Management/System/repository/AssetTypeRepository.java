package com.rsb.Asset.Management.System.repository;

import com.rsb.Asset.Management.System.models.entity.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetTypeRepository extends JpaRepository<AssetType, Integer> {
    List<AssetType> findByCategoryCategoryId(Integer categoryId);
    List<AssetType> findByCategoryCategoryName(String categoryName);
    
    @Query("SELECT at FROM AssetType at WHERE at.typeName = :typeName AND at.category.categoryId = :categoryId")
    Optional<AssetType> findByTypeNameAndCategoryId(@Param("typeName") String typeName, @Param("categoryId") Integer categoryId);
    
    boolean existsByTypeNameAndCategoryCategoryId(String typeName, Integer categoryId);
}
