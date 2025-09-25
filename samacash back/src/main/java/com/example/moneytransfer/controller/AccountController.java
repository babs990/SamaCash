package com.example.moneytransfer.controller;

import com.example.moneytransfer.dto.TransactionRequest;
import com.example.moneytransfer.entity.Account;
import com.example.moneytransfer.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // ✅ Récupérer le solde
    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@AuthenticationPrincipal User currentUser) {
        if (currentUser == null) {
            return ResponseEntity.status(403).body(Map.of("error", "Non authentifié"));
        }

        try {
            Account account = accountService.getAccountByEmail(currentUser.getUsername());
            return ResponseEntity.ok(
                    Map.of(
                            "balance", account.getBalance(),
                            "currency", account.getCurrency()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Erreur récupération solde"));
        }
    }

    // ✅ Dépôt d'argent
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@AuthenticationPrincipal User currentUser,
                                     @RequestBody TransactionRequest req) {
        if (currentUser == null) return ResponseEntity.status(403).body(Map.of("error", "Non authentifié"));

        try {
            accountService.deposit(currentUser.getUsername(), req.getAmount(), req.getDescription());
            return ResponseEntity.ok(Map.of("message", "Dépôt effectué"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ Retrait d'argent
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal User currentUser,
                                      @RequestBody TransactionRequest req) {
        if (currentUser == null) return ResponseEntity.status(403).body(Map.of("error", "Non authentifié"));

        try {
            accountService.withdraw(currentUser.getUsername(), req.getAmount(), req.getDescription());
            return ResponseEntity.ok(Map.of("message", "Retrait effectué"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }
}
