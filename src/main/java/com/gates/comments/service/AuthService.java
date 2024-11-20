package com.gates.comments.service;

import com.gates.comments.logs.RequestLoggingFilter;
import com.gates.comments.model.JwtResponse;
import com.gates.comments.model.TokenRefreshRequest;
import com.gates.comments.model.TokenRefreshResponse;
import com.gates.comments.model.User;
import com.gates.comments.repository.UserRepository;
import com.gates.comments.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RefreshTokenService refreshTokenService;

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    public ResponseEntity<String> registerUser(User user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            logger.info("asdfghjkl" + user.toString());
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(Instant.now());
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    public ResponseEntity<?> authenticateUser(User loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() ->
                new RuntimeException("Error: User not found."));
        String refreshToken = refreshTokenService.createRefreshToken(user.getId()).getToken();

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken, user.getId(), user.getUsername(), user.getEmail()));
    }

    public ResponseEntity<?> refreshToken(TokenRefreshRequest request) {
        String requestToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestToken)
                .map(refreshTokenService::verifyExpiration)
                .map(refreshToken -> {
                    String token = jwtUtils.generateTokenFromUsername(refreshToken.getUser().getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestToken));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }

    public ResponseEntity<String> logoutUser(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new RuntimeException("Error: User not found."));
        refreshTokenService.deleteByUserId(user.getId());
        return ResponseEntity.ok("User logged out successfully!");
    }
}
