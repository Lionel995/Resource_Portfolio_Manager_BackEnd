package com.rsb.Asset.Management.System.service;

import com.rsb.Asset.Management.System.models.entity.User;
import com.rsb.Asset.Management.System.repository.DivisionRepository;
import com.rsb.Asset.Management.System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DivisionRepository divisionRepository;

    public User createUser(User user) {
        user.setPasswordHash(passwordEncoder.encode("defaultPassword"));
        user.setFirstLogin(true);

        // Ensure Division exists if provided
        if (user.getDivision() != null && user.getDivision().getDivisionId() != null) {
            var division = divisionRepository.findById(user.getDivision().getDivisionId())
                    .orElseThrow(() -> new RuntimeException("Division not found"));
            user.setDivision(division);
        }

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void changePassword(User user, String newPassword) {
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setFirstLogin(false);
        userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public User getUserByEmail(String email) {
    return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        }

        public User saveUser(User user) {
    return userRepository.save(user);
}



    public User updateUser(Long userId, User updatedUser) {
    User existingUser = userRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

    // Update allowed fields
    existingUser.setFullName(updatedUser.getFullName());
    existingUser.setEmail(updatedUser.getEmail());
    existingUser.setPhone(updatedUser.getPhone());
    existingUser.setUsername(updatedUser.getUsername());
    existingUser.setRole(updatedUser.getRole());
    existingUser.setStatus(updatedUser.getStatus());

    // Update division if provided
    if (updatedUser.getDivision() != null && updatedUser.getDivision().getDivisionId() != null) {
        var division = divisionRepository.findById(updatedUser.getDivision().getDivisionId())
                .orElseThrow(() -> new RuntimeException("Division not found"));
        existingUser.setDivision(division);
    }

    return userRepository.save(existingUser);
}

public User getUserById(Long userId) {
    return userRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
}
    

}