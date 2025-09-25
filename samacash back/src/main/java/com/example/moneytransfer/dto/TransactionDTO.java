package com.example.moneytransfer.dto;

import com.example.moneytransfer.entity.Transaction;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class TransactionDTO {
    private Long id;
    private BigDecimal amount;
    private String type;
    private String description;
    private String fromEmail;
    private String toEmail;
    private LocalDateTime createdAt;

    public static TransactionDTO fromEntity(Transaction tx) {
        return TransactionDTO.builder()
                .id(tx.getId())
                .amount(tx.getAmount())
                .type(tx.getType())
                .description(tx.getDescription())
                .fromEmail(tx.getFromAccount() != null ? tx.getFromAccount().getUser().getEmail() : null)
                .toEmail(tx.getToAccount() != null ? tx.getToAccount().getUser().getEmail() : null)
                .createdAt(tx.getCreatedAt())
                .build();
    }
}
