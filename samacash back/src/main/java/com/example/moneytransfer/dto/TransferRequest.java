package com.example.moneytransfer.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransferRequest {
    private String toEmail;
    private BigDecimal amount;
    private String description;
}

