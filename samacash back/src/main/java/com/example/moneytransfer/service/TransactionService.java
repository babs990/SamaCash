package com.example.moneytransfer.service;

import com.example.moneytransfer.entity.*;
import com.example.moneytransfer.exception.ApiException;
import com.example.moneytransfer.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    /**
     * Récupère l'historique des transactions d'un utilisateur par email
     */
    public List<Transaction> getHistory(String email) {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("Utilisateur introuvable"));

        Account acct = accountRepository.findByUser(user)
                .orElseThrow(() -> new ApiException("Compte introuvable"));

        return transactionRepository.findByFromAccountOrToAccountOrderByCreatedAtDesc(acct, acct);
    }

    /**
     * Effectue un virement entre deux comptes
     */
    @Transactional
    public Transaction transfer(String fromEmail, String toEmail, BigDecimal amount, String description) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new ApiException("Montant invalide");

        AppUser fromUser = userRepository.findByEmail(fromEmail)
                .orElseThrow(() -> new ApiException("Expéditeur introuvable"));
        AppUser toUser = userRepository.findByEmail(toEmail)
                .orElseThrow(() -> new ApiException("Destinataire introuvable"));

        Account fromAcct = accountRepository.findByUser(fromUser)
                .orElseThrow(() -> new ApiException("Compte expéditeur introuvable"));
        Account toAcct = accountRepository.findByUser(toUser)
                .orElseThrow(() -> new ApiException("Compte destinataire introuvable"));

        if (fromAcct.getBalance().compareTo(amount) < 0) throw new ApiException("Solde insuffisant");

        // Frais : 1% du montant, minimum 100 XOF
        BigDecimal fees = amount.multiply(new BigDecimal("0.01"));
        if (fees.compareTo(new BigDecimal("100")) < 0) {
            fees = new BigDecimal("100");
        }

        // Débit expéditeur (montant + frais)
        fromAcct.setBalance(fromAcct.getBalance().subtract(amount).subtract(fees));
        // Crédit destinataire (montant)
        toAcct.setBalance(toAcct.getBalance().add(amount));

        accountRepository.save(fromAcct);
        accountRepository.save(toAcct);

        Transaction t = Transaction.builder()
                .amount(amount)
                .fee(fees)
                .createdAt(LocalDateTime.now())
                .fromAccount(fromAcct)
                .toAccount(toAcct)
                .type("TRANSFER")
                .description(description + " | Frais: " + fees + " XOF")
                .build();

        return transactionRepository.save(t);
    }

    /**
     * Dépôt d'argent
     */
    @Transactional
    public Transaction deposit(String email, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new ApiException("Montant invalide");

        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("Utilisateur introuvable"));
        Account acct = accountRepository.findByUser(user)
                .orElseThrow(() -> new ApiException("Compte introuvable"));

        acct.setBalance(acct.getBalance().add(amount));
        accountRepository.save(acct);

        Transaction t = Transaction.builder()
                .amount(amount)
                .fee(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .fromAccount(null)
                .toAccount(acct)
                .type("DEPOSIT")
                .description("Dépôt de " + amount + " XOF")
                .build();

        return transactionRepository.save(t);
    }

    /**
     * Retrait d'argent
     */
    @Transactional
    public Transaction withdraw(String email, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new ApiException("Montant invalide");

        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("Utilisateur introuvable"));
        Account acct = accountRepository.findByUser(user)
                .orElseThrow(() -> new ApiException("Compte introuvable"));

        if (acct.getBalance().compareTo(amount) < 0) throw new ApiException("Solde insuffisant");

        acct.setBalance(acct.getBalance().subtract(amount));
        accountRepository.save(acct);

        Transaction t = Transaction.builder()
                .amount(amount)
                .fee(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .fromAccount(acct)
                .toAccount(null)
                .type("WITHDRAW")
                .description("Retrait de " + amount + " XOF")
                .build();

        return transactionRepository.save(t);
    }
}

