package com.socialxchange.soco_backend.config.database.repositories;

import com.socialxchange.soco_backend.config.database.entities.VTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VTransactionRepository extends JpaRepository<VTransaction, Long> {

    @Query("SELECT t FROM VTransaction t WHERE t.influencer.id = :influencerId AND t.timestamp >= :startDate AND t.timestamp < :endDate ORDER BY t.timestamp DESC")
    List<VTransaction> findCurrentMonthTransactionsByInfluencerId(@Param("influencerId") Long influencerId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT t FROM VTransaction t WHERE t.business.id = :businessId AND t.timestamp >= :startDate AND t.timestamp < :endDate ORDER BY t.timestamp DESC")
    List<VTransaction> findCurrentMonthTransactionsByBusinessId(@Param("businessId") Long businessId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT SUM(v.amount) FROM VTransaction v WHERE v.influencer.id = :influencerId AND EXTRACT(MONTH FROM v.timestamp) = EXTRACT(MONTH FROM CURRENT_DATE) AND EXTRACT(YEAR FROM v.timestamp) = EXTRACT(YEAR FROM CURRENT_DATE)")
    Optional<Double> getMonthlySpendForInfluencer(@Param("influencerId") Long influencerId);

}