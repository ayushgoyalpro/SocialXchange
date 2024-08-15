package com.socialxchange.soco_backend.config.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.socialxchange.soco_backend.config.Utility;
import com.socialxchange.soco_backend.config.exceptions.InternalException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "business")
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String location;

    private String contact;

    @Column(nullable = false, unique = true)
    private String email;

    @Transient
    @JsonIgnore
    private String password;

    @Column(name = "password_hash", nullable = false)
    @JsonIgnore
    private String passwordHash;

    private String qr;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() throws InternalException {
        timestamp = LocalDateTime.now();
        passwordHash = Utility.hashPassword(password);
    }
}