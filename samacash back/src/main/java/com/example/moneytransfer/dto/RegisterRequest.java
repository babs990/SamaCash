package com.example.moneytransfer.dto;
import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    // private String country;
    // private String identityNumber;
}
