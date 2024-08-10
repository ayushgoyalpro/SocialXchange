package com.socialxchange.soco_backend.config.database.repositories;

import com.socialxchange.soco_backend.config.database.entities.PaymentCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {
    // Additional query methods (if needed) can be defined here
}
