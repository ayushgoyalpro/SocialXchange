package com.socialxchange.soco_backend.config.database.repositories;

import com.socialxchange.soco_backend.config.database.entities.SoCoTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoCoTransactionRepository extends JpaRepository<SoCoTransaction, Long> {
    // Additional query methods (if needed) can be defined here
}
