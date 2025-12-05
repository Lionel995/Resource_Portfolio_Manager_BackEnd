package com.rsb.Asset.Management.System.repository;

import com.rsb.Asset.Management.System.models.entity.Asset;
import com.rsb.Asset.Management.System.models.enums.AssetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Integer> {
    Optional<Asset> findByAssetNumber(String assetNumber);
    boolean existsByAssetNumber(String assetNumber);
    
    List<Asset> findByStatus(AssetStatus status);
    List<Asset> findByCategoryCategoryId(Integer categoryId);
    List<Asset> findByTypeTypeId(Integer typeId);
    List<Asset> findByCategoryCategoryName(String categoryName);
    List<Asset> findByTypeTypeName(String typeName);
    
    @Query("SELECT a FROM Asset a WHERE a.status = :status AND a.category.categoryId = :categoryId")
    List<Asset> findByStatusAndCategoryId(@Param("status") AssetStatus status, @Param("categoryId") Integer categoryId);
    
    @Query("SELECT a FROM Asset a WHERE a.status = :status AND a.type.typeId = :typeId")
    List<Asset> findByStatusAndTypeId(@Param("status") AssetStatus status, @Param("typeId") Integer typeId);
    
    @Query("SELECT a FROM Asset a WHERE a.registrationDate BETWEEN :startDate AND :endDate")
    List<Asset> findByRegistrationDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT a FROM Asset a WHERE a.registeredBy.userId = :userId")
    List<Asset> findByRegisteredByUserId(@Param("userId") Integer userId);
    
    @Query("SELECT COUNT(a) FROM Asset a WHERE a.status = :status")
    Long countByStatus(@Param("status") AssetStatus status);
}
