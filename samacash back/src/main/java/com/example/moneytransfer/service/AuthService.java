package com.example.moneytransfer.service;

import com.example.moneytransfer.dto.*;
import com.example.moneytransfer.entity.*;
import com.example.moneytransfer.repository.*;
import com.example.moneytransfer.config.JwtConfig;
import com.example.moneytransfer.exception.ApiException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new ApiException("Email déjà utilisé");
        }

        // 🔹 Crée l'utilisateur
        AppUser user = AppUser.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .role(AppUser.Role.USER)
                .build();

        user = userRepository.save(user);

        // 🔹 Crée automatiquement un compte avec solde initial
        Account acct = Account.builder()
                .user(user)
                .balance(BigDecimal.valueOf(1000)) // 👈 change 1000 en ZERO si tu veux pas de prime
                .currency("XOF")
                .build();

        accountRepository.save(acct);

        // 🔹 Génère un token JWT
        String token = jwtConfig.generateToken(user.getEmail());
        return new AuthResponse(token, "Bearer");
    }

    public AuthResponse login(AuthRequest req) {
        System.out.println("📩 Email reçu: " + req.getEmail());
        System.out.println("📩 Password reçu: " + req.getPassword());

        AppUser user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new ApiException("Utilisateur introuvable"));

        System.out.println("🔐 Password en DB: " + user.getPassword());

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new ApiException("Mot de passe invalide");
        }

        String token = jwtConfig.generateToken(user.getEmail());
        return new AuthResponse(token, "Bearer");
    }
}

