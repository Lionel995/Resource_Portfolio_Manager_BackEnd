package com.rsb.Asset.Management.System.controller;

import com.rsb.Asset.Management.System.models.entity.User;
import com.rsb.Asset.Management.System.models.dto.AuthRequest;
import com.rsb.Asset.Management.System.models.dto.OtpDTO;
import com.rsb.Asset.Management.System.models.dto.EmailDTO;
import com.rsb.Asset.Management.System.repository.UserRepository;
import com.rsb.Asset.Management.System.security.JwtUtil;
import com.rsb.Asset.Management.System.service.EmailService;
import com.rsb.Asset.Management.System.service.PasswordResetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserRepository userRepository;
    @Autowired private EmailService emailService;
    @Autowired private PasswordResetService resetService;

    @PostMapping("/verify_credentials")
    public ResponseEntity<?> verifyCredentials(@RequestBody AuthRequest request) {
    try {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String email = authentication.getName(); 
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        String otp = generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);

        emailService.sendOtpEmail(user.getEmail(), otp);
        return ResponseEntity.ok("OTP sent to email");

    } catch (AuthenticationException ex) {
        return ResponseEntity.status(403).body("Invalid credentials or user not authorized");
    }
}



    @PostMapping("/confirm-otp")
public ResponseEntity<?> confirmOtp(@RequestBody OtpDTO dto) {
    User user = userRepository.findByEmail(dto.getEmail()).orElse(null);

    if (user == null || user.getOtp() == null) {
        return ResponseEntity.badRequest().body("Invalid OTP");
    }

    if (!user.getOtp().equals(dto.getOtp()) || user.getOtpExpiry().isBefore(LocalDateTime.now())) {
        return ResponseEntity.badRequest().body("OTP expired or invalid");
    }

    user.setOtp(null);
    user.setOtpExpiry(null);
    userRepository.save(user);

    String token = jwtUtil.generateTokenWithUserRole(user.getEmail(), user.getUsername(), user.getRole().name());

    return ResponseEntity.ok(Map.of(
        "token", token,
        "username", user.getUsername(),
        "email", user.getEmail(),
        "role", user.getRole().name().toLowerCase(),
        "firstLogin", user.isFirstLogin(),
        "division", user.getDivision() != null ? Map.of(
            "divisionId", user.getDivision().getDivisionId(),
            "divisionName", user.getDivision().getDivisionName()
        ) : null
    ));
}


    @PostMapping("/request-reset")
    public ResponseEntity<?> requestReset(@RequestBody EmailDTO request) {
        return ResponseEntity.ok(resetService.createPasswordResetToken(request.getEmail()));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        return ResponseEntity.ok(resetService.resetPassword(token, newPassword));
    }

    @PostMapping("/login_direct")
    public ResponseEntity<?> loginDirect(@RequestBody AuthRequest request) {
    try {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String email = authentication.getName(); 
        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null) {
            String token = jwtUtil.generateTokenWithUserRole(
                user.getEmail(), user.getUsername(), user.getRole().name()
            );
            return ResponseEntity.ok(Map.of(
                "token", token,
                "username", user.getUsername(),
                "email", user.getEmail(),
                "role", user.getRole().name().toLowerCase(),
                "firstLogin", user.isFirstLogin()

            ));
        }

        return ResponseEntity.badRequest().body("User not found");

    } catch (AuthenticationException ex) {
        return ResponseEntity.status(403).body("Invalid credentials: " + ex.getMessage());
    }
}



    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestBody Map<String, String> request) {
        try {
            String token = request.get("token");
            if (token == null) {
                return ResponseEntity.badRequest().body("Token is required");
            }

            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractRole(token);

                return ResponseEntity.ok(Map.of(
                    "valid", true,
                    "username", username != null ? username : "null",
                    "role", role != null ? role : "null"
                ));
            } else {
                return ResponseEntity.ok(Map.of("valid", false, "message", "Token is invalid or expired"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error validating token: " + e.getMessage());
        }
    }

    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}