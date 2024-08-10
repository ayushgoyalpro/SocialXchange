package com.socialxchange.soco_backend.config.database.repositories;

import com.socialxchange.soco_backend.config.database.entities.BusinessTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessTransferRepository extends JpaRepository<BusinessTransfer, Long> {
    // Additional query methods (if needed) can be defined here
}
