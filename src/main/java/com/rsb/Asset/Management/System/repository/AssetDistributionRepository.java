package com.rsb.Asset.Management.System.repository;

import com.rsb.Asset.Management.System.models.entity.AssetDistribution;
//import com.rsb.Asset.Management.System.models.enums.DistributionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
//import java.util.Optional;

@Repository
public interface AssetDistributionRepository extends JpaRepository<AssetDistribution, Integer> {
    //List<AssetDistribution> findByStatus(DistributionStatus status);
    List<AssetDistribution> findByDistributedToUserId(Integer userId);
    List<AssetDistribution> findByAssetAssetId(Integer assetId);
    List<AssetDistribution> findByRequestRequestId(Integer requestId);
    
    // @Query("SELECT ad FROM AssetDistribution ad WHERE ad.distributedTo.userId = :userId AND ad.status = :status")
    // List<AssetDistribution> findByUserIdAndStatus(@Param("userId") Integer userId, @Param("status") DistributionStatus status);
    
    // @Query("SELECT ad FROM AssetDistribution ad WHERE ad.asset.assetId = :assetId AND ad.status = :status")
    // Optional<AssetDistribution> findCurrentDistributionByAssetId(@Param("assetId") Integer assetId, @Param("status") DistributionStatus status);
    
    @Query("SELECT ad FROM AssetDistribution ad WHERE ad.distributionDate BETWEEN :startDate AND :endDate")
    List<AssetDistribution> findByDistributionDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ad FROM AssetDistribution ad WHERE ad.returnedDate BETWEEN :startDate AND :endDate")
    List<AssetDistribution> findByReturnDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // @Query("SELECT COUNT(ad) FROM AssetDistribution ad WHERE ad.status = :status")
    // Long countByStatus(@Param("status") DistributionStatus status);
    
    // @Query("SELECT ad FROM AssetDistribution ad WHERE ad.distributedTo.division.divisionId = :divisionId AND ad.status = :status")
    // List<AssetDistribution> findByDivisionAndStatus(@Param("divisionId") Integer divisionId, @Param("status") DistributionStatus status);
}
