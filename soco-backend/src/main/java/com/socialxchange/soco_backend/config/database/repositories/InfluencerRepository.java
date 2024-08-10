package com.socialxchange.soco_backend.config.database.repositories;

import com.socialxchange.soco_backend.config.database.entities.Influencer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfluencerRepository extends JpaRepository<Influencer, Long> {
    // Additional query methods (if needed) can be defined here
}
