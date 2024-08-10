package com.socialxchange.soco_backend.config.database.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "influencer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Influencer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ig_username", nullable = false, length = 255)
    private String igUsername;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "mobile_number", nullable = false, unique = true, length = 20)
    private String mobileNumber;

    @Transient
    private String password;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column
    private Integer followers;

    @Column(length = 255)
    private String address;

    @Column
    private Double lat;

    @Column
    private Double lon;

    @Column
    private LocalDate dob;

    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @PrePersist
    protected void onCreate() {
        dateCreated = LocalDate.now();
    }
}
