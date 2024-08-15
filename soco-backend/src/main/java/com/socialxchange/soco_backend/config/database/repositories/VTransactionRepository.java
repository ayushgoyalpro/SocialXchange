package com.socialxchange.soco_backend.config.database.repositories;

import com.socialxchange.soco_backend.config.database.entities.VTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VTransactionRepository extends JpaRepository<VTransaction, Long> {

    @Query("SELECT t FROM VTransaction t WHERE t.influencer.id = :influencerId AND t.timestamp >= :startDate AND t.timestamp < :endDate")
    List<VTransaction> findCurrentMonthTransactionsByInfluencerId(@Param("influencerId") Long influencerId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}