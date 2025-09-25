package com.example.moneytransfer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AppUser {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String firstName;
    private String lastName;
    private String country;
    private String identityNumber;
    private String photoUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role { USER, ADMIN }

    // ✅ Relation avec Account
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Account account;
}
