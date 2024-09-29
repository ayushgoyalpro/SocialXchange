package com.socialxchange.soco_backend.service;

import com.socialxchange.soco_backend.config.database.entities.Business;
import com.socialxchange.soco_backend.config.database.entities.Influencer;
import com.socialxchange.soco_backend.config.database.entities.VTransaction;
import com.socialxchange.soco_backend.config.database.repositories.BusinessRepository;
import com.socialxchange.soco_backend.config.database.repositories.InfluencerRepository;
import com.socialxchange.soco_backend.config.database.repositories.VTransactionRepository;
import com.socialxchange.soco_backend.config.dto.Payment;
import com.socialxchange.soco_backend.config.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InfluencerService {

    private final InfluencerRepository influencerRepository;
    private final BusinessRepository businessRepository;
    private final VTransactionRepository transactionRepository;


    public InfluencerService(InfluencerRepository influencerRepository, BusinessRepository businessRepository, VTransactionRepository transactionRepository) {
        this.influencerRepository = influencerRepository;
        this.businessRepository = businessRepository;
        this.transactionRepository = transactionRepository;
    }


    public List<VTransaction> getTransactions(User user) {
        YearMonth currentMonth = YearMonth.now();
        LocalDateTime startDate = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = currentMonth.atEndOfMonth().atTime(23, 59, 59);
        return transactionRepository.findCurrentMonthTransactionsByInfluencerId(user.getId(), startDate, endDate);
    }

    public Double getBalance(User user) {
        Optional<Double> spend = transactionRepository.getMonthlySpendForInfluencer(user.getId());
        return spend.orElse(0.0);
    }

    public boolean makePayment(User user, Payment payment) {
        Influencer influencer = influencerRepository.findById(user.getId()).orElseThrow(() -> new InternalException("influencer_not_found"));
        Business business = businessRepository.findById(payment.getBusinessId()).orElseThrow(() -> new InternalException("business_not_found"));
        VTransaction transaction = new VTransaction();
        transaction.setInfluencer(influencer);
        transaction.setBusiness(business);
        transaction.setAmount(payment.getAmount());
        transactionRepository.save(transaction);
        return true;
    }
}
