package com.rsb.Asset.Management.System.controller;

import com.rsb.Asset.Management.System.models.entity.User;
import com.rsb.Asset.Management.System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public void changePassword(@RequestParam String newPassword, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        userService.changePassword(user, newPassword);
    }

    @PutMapping("/first-login/change-password")
@PreAuthorize("isAuthenticated()")
public String changeDefaultPassword(@RequestParam String newPassword, Principal principal) {
    User user = userService.getUserByEmail(principal.getName());

    if (!user.isFirstLogin()) {
        return "Password already changed. Not first login anymore.";
    }

    userService.changePassword(user, newPassword);

    // update firstLogin flag
    user.setFirstLogin(false);
    userService.saveUser(user);

    return "Password changed successfully. First login completed!";
}


@PutMapping("/update/{id}")
@PreAuthorize("hasRole('ADMIN')")
public User updateUser(@PathVariable Long id, @RequestBody User user) {
    return userService.updateUser(id, user);
}


@GetMapping("/{id}")
@PreAuthorize("hasRole('ADMIN')")
public User getUserById(@PathVariable Long id) {
    return userService.getUserById(id);
}

}