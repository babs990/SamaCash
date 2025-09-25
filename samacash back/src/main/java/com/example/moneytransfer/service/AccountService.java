package com.example.moneytransfer.service;

import com.example.moneytransfer.entity.*;
import com.example.moneytransfer.repository.*;
import com.example.moneytransfer.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public Account getAccountByEmail(String email) {
        AppUser user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException("Utilisateur introuvable"));
        return accountRepository.findByUser(user).orElseThrow(() -> new ApiException("Compte introuvable"));
    }

    @Transactional
    public void deposit(String email, BigDecimal amount, String description) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new ApiException("Montant invalide");
        Account acct = getAccountByEmail(email);
        acct.setBalance(acct.getBalance().add(amount));
        accountRepository.save(acct);

        Transaction t = Transaction.builder()
            .amount(amount)
            .fee(java.math.BigDecimal.ZERO)
            .createdAt(LocalDateTime.now())
            .fromAccount(null)
            .toAccount(acct)
            .type("DEPOSIT")
            .description(description)
            .build();
        transactionRepository.save(t);
    }

    @Transactional
    public void withdraw(String email, BigDecimal amount, String description) {
        Account acct = getAccountByEmail(email);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new ApiException("Montant invalide");
        if (acct.getBalance().compareTo(amount) < 0) throw new ApiException("Solde insuffisant");
        acct.setBalance(acct.getBalance().subtract(amount));
        accountRepository.save(acct);

        Transaction t = Transaction.builder()
                .amount(amount)
                .fee(java.math.BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .fromAccount(acct)
                .toAccount(null)
                .type("WITHDRAWAL")
                .description(description)
                .build();
        transactionRepository.save(t);
    }
}
