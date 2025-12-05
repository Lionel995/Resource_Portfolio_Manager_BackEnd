package com.rsb.Asset.Management.System.repository;

import com.rsb.Asset.Management.System.models.entity.PasswordResetToken;
import com.rsb.Asset.Management.System.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUser(User user);
}