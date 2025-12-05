package com.rsb.Asset.Management.System.service;

import com.rsb.Asset.Management.System.models.entity.User;
import com.rsb.Asset.Management.System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired 
    private UserRepository userRepository;

    @Autowired 
    private EmailService emailService;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    public String createPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return "User not found";

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(10));
        userRepository.save(user);

        emailService.sendResetLink(email, token);
        return "Reset link sent";
    }

    public String resetPassword(String token, String newPassword) {
        Optional<User> userOpt = userRepository.findAll().stream()
            .filter(u -> token.equals(u.getResetToken()) && u.getResetTokenExpiry() != null && u.getResetTokenExpiry().isAfter(LocalDateTime.now()))
            .findFirst();

        if (userOpt.isEmpty()) return "Invalid or expired token";

        User user = userOpt.get();
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);

        return "Password reset successful";
    }
}