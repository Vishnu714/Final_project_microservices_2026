package com.example.auth.service;

import com.example.auth.dto.*;
import com.example.auth.model.*;
import com.example.auth.repository.*;
import com.example.auth.config.JwtConfig;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    public AuthServiceImpl(UserRepository userRepo,
                           RoleRepository roleRepo,
                           PasswordEncoder passwordEncoder,
                           JwtConfig jwtConfig) {

        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtConfig = jwtConfig;
    }

    @Override
    public AuthResponseDto register(RegisterRequestDto request) {

        if (userRepo.existsByUsername(request.username())) {
            throw new RuntimeException("Username already exists");
        }

      
        Role userRole = roleRepo.findByName("ROLE_USER")
                .orElseGet(() ->
                        roleRepo.save(new Role(null, "ROLE_USER")));

        Role adminRole = roleRepo.findByName("ROLE_ADMIN")
                .orElseGet(() ->
                        roleRepo.save(new Role(null, "ROLE_ADMIN")));

     
        Role assignedRole;

        if (request.username().equalsIgnoreCase("admin")) {
            assignedRole = adminRole;
        } else {
            assignedRole = userRole;
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setRoles(Set.of(assignedRole));

        userRepo.save(user);

        String token = jwtConfig.generateToken(
                user.getUsername(),
                user.getRoles().stream().toList()
        );

        return new AuthResponseDto(token, user.getUsername());
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {

        User user = userRepo.findByUsername(request.username())
                .orElseThrow(() ->
                        new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(
                request.password(),
                user.getPassword())) {

            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtConfig.generateToken(
                user.getUsername(),
                user.getRoles().stream().toList()
        );

        return new AuthResponseDto(token, user.getUsername());
    }
}
