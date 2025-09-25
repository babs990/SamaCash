package com.example.moneytransfer.dto;
import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";
}
