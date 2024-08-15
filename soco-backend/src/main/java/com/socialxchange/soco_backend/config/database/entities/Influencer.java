package com.socialxchange.soco_backend.config.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.socialxchange.soco_backend.config.Utility;
import com.socialxchange.soco_backend.config.exceptions.InternalException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "influencer")
public class Influencer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ig_username", nullable = false)
    private String igUsername;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    private String contact;

    @Column(nullable = false, unique = true)
    private String email;

    @Transient
    @JsonIgnore
    private String password;

    @Column(name = "password_hash", nullable = false)
    @JsonIgnore
    private String passwordHash;

    @Column(name = "credit_limit")
    private BigDecimal creditLimit;

    private LocalDate dob;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() throws InternalException {
        timestamp = LocalDateTime.now();
        passwordHash = Utility.hashPassword(password);
    }
}