package com.socialxchange.soco_backend.config.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.socialxchange.soco_backend.config.Utility;
import com.socialxchange.soco_backend.config.exceptions.InternalException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "maillist")
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() throws InternalException {
        timestamp = LocalDateTime.now();
    }
}