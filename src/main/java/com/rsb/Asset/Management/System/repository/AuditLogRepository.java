package com.rsb.Asset.Management.System.repository;

import com.rsb.Asset.Management.System.models.entity.AuditLog;
import com.rsb.Asset.Management.System.models.enums.AuditAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {
    List<AuditLog> findByUserUserId(Integer userId);
    List<AuditLog> findByAction(AuditAction action);
    
    @Query("SELECT al FROM AuditLog al WHERE al.timestamp BETWEEN :startDate AND :endDate ORDER BY al.timestamp DESC")
    List<AuditLog> findByTimestampBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT al FROM AuditLog al WHERE al.user.userId = :userId AND al.action = :action")
    List<AuditLog> findByUserIdAndAction(@Param("userId") Integer userId, @Param("action") AuditAction action);
    
    @Query("SELECT al FROM AuditLog al WHERE al.user.division.divisionId = :divisionId ORDER BY al.timestamp DESC")
    List<AuditLog> findByDivisionId(@Param("divisionId") Integer divisionId);
    
    @Query("SELECT al FROM AuditLog al ORDER BY al.timestamp DESC")
    List<AuditLog> findAllOrderByTimestampDesc();
    
    @Query("SELECT COUNT(al) FROM AuditLog al WHERE al.action = :action")
    Long countByAction(@Param("action") AuditAction action);
}
