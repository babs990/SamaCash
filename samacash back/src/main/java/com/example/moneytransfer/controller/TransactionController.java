package com.example.moneytransfer.controller;

import com.example.moneytransfer.dto.TransactionDTO;
import com.example.moneytransfer.dto.TransferRequest;
import com.example.moneytransfer.dto.TransactionRequest;
import com.example.moneytransfer.entity.Transaction;
import com.example.moneytransfer.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public List<TransactionDTO> getHistory(@AuthenticationPrincipal User currentUser) {
        List<Transaction> txs = transactionService.getHistory(currentUser.getUsername());
        return txs.stream().map(TransactionDTO::fromEntity).collect(Collectors.toList());
    }

    @PostMapping("/send")
    public TransactionDTO transfer(@AuthenticationPrincipal User currentUser, @RequestBody TransferRequest request) {
        Transaction tx = transactionService.transfer(
                currentUser.getUsername(),
                request.getToEmail(),
                request.getAmount(),
                request.getDescription()
        );
        return TransactionDTO.fromEntity(tx);
    }

    @PostMapping("/deposit")
    public TransactionDTO deposit(@AuthenticationPrincipal User currentUser, @RequestBody TransactionRequest request) {
        Transaction tx = transactionService.deposit(currentUser.getUsername(), request.getAmount());
        return TransactionDTO.fromEntity(tx);
    }

    @PostMapping("/withdraw")
    public TransactionDTO withdraw(@AuthenticationPrincipal User currentUser, @RequestBody TransactionRequest request) {
        Transaction tx = transactionService.withdraw(currentUser.getUsername(), request.getAmount());
        return TransactionDTO.fromEntity(tx);
    }
}



