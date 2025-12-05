package com.rsb.Asset.Management.System.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.rsb.Asset.Management.System.models.entity.User;
import com.rsb.Asset.Management.System.repository.UserRepository;
//import com.rsb.Asset.Management.System.models.enums.UserRole;

@Primary
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(usernameOrEmail)
                .orElse(userRepository.findByEmail(usernameOrEmail)
                .orElse(null));

        if (user != null) {
            String authority = "ROLE_" + user.getRole().name();
            return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                List.of(new SimpleGrantedAuthority(authority))
            );
        }

        throw new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail);
    }
}