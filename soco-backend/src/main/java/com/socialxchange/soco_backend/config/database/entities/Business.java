package com.socialxchange.soco_backend.config.database.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "business")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Transient
    private String password;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "business_type", length = 50)
    private String businessType;

    @Column(name = "ig_username", length = 255)
    private String igUsername;

    @Column(length = 255)
    private String address;

    @Column
    private Double lat;

    @Column
    private Double lon;

    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;

    @Column(length = 255)
    private String qr;

    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @Column(length = 50)
    private String status;

    @PrePersist
    protected void onCreate() {
        dateCreated = LocalDate.now();
    }
}
