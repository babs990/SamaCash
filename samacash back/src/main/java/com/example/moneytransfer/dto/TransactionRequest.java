package com.example.moneytransfer.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private String email;
    private BigDecimal amount;
    private String description;
}

