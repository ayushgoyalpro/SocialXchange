package com.socialxchange.soco_backend.config.database.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "business_id", referencedColumnName = "id")
    private Business business;

    @Column(name = "influencer_count")
    private Integer influencerCount;

    @Column(name = "promotions_count")
    private Integer promotionsCount;

    @Column(name = "audience_reached")
    private Integer audienceReached;
}
