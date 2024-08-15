package com.socialxchange.soco_backend.service;

import com.socialxchange.soco_backend.config.database.entities.Business;
import com.socialxchange.soco_backend.config.database.entities.VTransaction;
import com.socialxchange.soco_backend.config.database.repositories.BusinessRepository;
import com.socialxchange.soco_backend.config.database.repositories.InfluencerRepository;
import com.socialxchange.soco_backend.config.database.repositories.VTransactionRepository;
import com.socialxchange.soco_backend.config.dto.User;
import com.socialxchange.soco_backend.config.exceptions.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BusinessService {

    private final InfluencerRepository influencerRepository;
    private final BusinessRepository businessRepository;
    private final VTransactionRepository transactionRepository;


    public BusinessService(InfluencerRepository influencerRepository, BusinessRepository businessRepository, VTransactionRepository transactionRepository) {
        this.influencerRepository = influencerRepository;
        this.businessRepository = businessRepository;
        this.transactionRepository = transactionRepository;
    }


    public List<VTransaction> getTransactions(User user) {
        YearMonth currentMonth = YearMonth.now();
        LocalDateTime startDate = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = currentMonth.atEndOfMonth().atTime(23, 59, 59);
        return transactionRepository.findCurrentMonthTransactionsByBusinessId(user.getId(), startDate, endDate);
    }

    public String getQr(User user) throws InternalException {
        Optional<Business> business = businessRepository.findById(user.getId());
        if (business.isPresent()) {
            return business.get().getQr();
        }
        else {
            throw new InternalException("business_not_found");
        }
    }
}
