package com.experimentintelligence.auth.service;

import com.experimentintelligence.auth.dto.AuthResponse;
import com.experimentintelligence.auth.dto.LoginRequest;
import com.experimentintelligence.auth.dto.RegisterRequest;
import com.experimentintelligence.auth.entity.Organization;
import com.experimentintelligence.auth.entity.Role;
import com.experimentintelligence.auth.entity.SubscriptionTier;
import com.experimentintelligence.auth.entity.User;
import com.experimentintelligence.auth.repository.OrganizationRepository;
import com.experimentintelligence.auth.repository.UserRepository;
import com.experimentintelligence.auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getAdminEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        if (organizationRepository.findByName(request.getOrgName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Organization name already taken");
        }

        Organization organization = Organization.builder()
                .name(request.getOrgName())
                .subscriptionTier(SubscriptionTier.FREE)
                .build();
        organization = organizationRepository.save(organization);

        User user = User.builder()
                .email(request.getAdminEmail())
                .passwordHash(passwordEncoder.encode(request.getAdminPassword()))
                .role(Role.ORG_ADMIN)
                .organization(organization)
                .build();
        user = userRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .organizationId(organization.getId().toString())
                .subscriptionTier(organization.getSubscriptionTier().name())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .organizationId(user.getOrganization().getId().toString())
                .subscriptionTier(user.getOrganization().getSubscriptionTier().name())
                .build();
    }
}
