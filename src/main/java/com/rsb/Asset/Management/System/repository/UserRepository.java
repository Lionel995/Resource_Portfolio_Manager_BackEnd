package com.rsb.Asset.Management.System.repository;

import com.rsb.Asset.Management.System.models.entity.User;
import com.rsb.Asset.Management.System.models.enums.UserRole;
import com.rsb.Asset.Management.System.models.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    Optional<User> findByUserId(Long userId);
    
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    
    List<User> findByStatus(UserStatus status);
    List<User> findByDivisionDivisionName(String divisionName);
    List<User> findByRole(UserRole role);

    @Query("SELECT u FROM User u WHERE u.role = :role AND u.status = :status")
    List<User> findActiveUsersByRole(@Param("role") UserRole role, @Param("status") UserStatus status);
    
    @Query("SELECT u FROM User u WHERE u.division.divisionName = :divisionName AND u.status = :status")
    List<User> findActiveUsersByDivision(@Param("divisionName") String divisionName, @Param("status") UserStatus status);
}
