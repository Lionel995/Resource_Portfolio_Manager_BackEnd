package com.rsb.Asset.Management.System.repository;

import com.rsb.Asset.Management.System.models.entity.RequestApproval;
import com.rsb.Asset.Management.System.models.enums.ApprovalDecision;
import com.rsb.Asset.Management.System.models.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RequestApprovalRepository extends JpaRepository<RequestApproval, Integer> {
    List<RequestApproval> findByRequestRequestId(Integer requestId);
    List<RequestApproval> findByApprovedByUserId(Integer userId);
    List<RequestApproval> findByApprovalRole(UserRole approvalRole); // Changed here
    List<RequestApproval> findByDecision(ApprovalDecision decision);

    @Query("SELECT ra FROM RequestApproval ra WHERE ra.request.requestId = :requestId AND ra.approvalRole = :role")
    Optional<RequestApproval> findByRequestIdAndApprovalRole(@Param("requestId") Integer requestId, @Param("role") UserRole role);

    @Query("SELECT ra FROM RequestApproval ra WHERE ra.approvedBy.userId = :userId AND ra.decision = :decision")
    List<RequestApproval> findByApproverAndDecision(@Param("userId") Integer userId, @Param("decision") ApprovalDecision decision);

    @Query("SELECT ra FROM RequestApproval ra WHERE ra.approvalDate BETWEEN :startDate AND :endDate")
    List<RequestApproval> findByApprovalDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(ra) FROM RequestApproval ra WHERE ra.approvalRole = :role AND ra.decision = :decision")
    Long countByRoleAndDecision(@Param("role") UserRole role, @Param("decision") ApprovalDecision decision);
}
