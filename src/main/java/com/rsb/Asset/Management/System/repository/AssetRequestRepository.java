package com.rsb.Asset.Management.System.repository;

import com.rsb.Asset.Management.System.models.entity.Asset;
import com.rsb.Asset.Management.System.models.entity.AssetRequest;
import com.rsb.Asset.Management.System.models.entity.User;
import com.rsb.Asset.Management.System.models.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AssetRequestRepository extends JpaRepository<AssetRequest, Integer> {
    List<AssetRequest> findByCurrentStatus(RequestStatus status);
    List<AssetRequest> findByRequestedByUserId(Integer userId);
    List<AssetRequest> findByAssetAssetId(Integer assetId);
    
    boolean existsByRequestedByAndAssetAndCurrentStatus(User requestedBy, Asset asset, RequestStatus status);
    
    @Query("SELECT ar FROM AssetRequest ar WHERE ar.requestedBy.userId = :userId AND ar.currentStatus = :status")
    List<AssetRequest> findByUserIdAndStatus(@Param("userId") Integer userId, @Param("status") RequestStatus status);
    
    @Query("SELECT ar FROM AssetRequest ar WHERE ar.requestDate BETWEEN :startDate AND :endDate")
    List<AssetRequest> findByRequestDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ar FROM AssetRequest ar WHERE ar.requestedBy.division.divisionId = :divisionId AND ar.currentStatus = :status")
    List<AssetRequest> findByDivisionAndStatus(@Param("divisionId") String divisionName, @Param("status") RequestStatus status);
    
    @Query("SELECT COUNT(ar) FROM AssetRequest ar WHERE ar.currentStatus = :status")
    Long countByStatus(@Param("status") RequestStatus status);
    
    @Query("SELECT ar FROM AssetRequest ar WHERE ar.currentStatus IN :statuses ORDER BY ar.requestDate DESC")
    List<AssetRequest> findPendingRequests(@Param("statuses") List<RequestStatus> statuses);

    //List<AssetRequest> findByRequestedByUserId(Integer userId);

    List<AssetRequest> findByRequestedBy_Division_DivisionName(String divisionName);

    @Query("SELECT ar FROM AssetRequest ar WHERE ar.requestedBy.division.divisionName = :divisionName")
List<AssetRequest> findRequestsByDivisionName(@Param("divisionName") String divisionName);


    //List<AssetRequest> findByCurrentStatus(RequestStatus status);

    //boolean existsByRequestedByAndAssetAndCurrentStatus(User user, Asset asset, RequestStatus status);
}
