package com.example.moneytransfer.repository;

import com.example.moneytransfer.entity.Transaction;
import com.example.moneytransfer.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByFromAccountOrToAccountOrderByCreatedAtDesc(Account from, Account to);
}
