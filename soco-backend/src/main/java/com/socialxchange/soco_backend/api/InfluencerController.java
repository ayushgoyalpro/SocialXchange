package com.socialxchange.soco_backend.api;

import com.socialxchange.soco_backend.config.Utility;
import com.socialxchange.soco_backend.config.database.entities.VTransaction;
import com.socialxchange.soco_backend.config.database.repositories.VTransactionRepository;
import com.socialxchange.soco_backend.config.dto.User;
import com.socialxchange.soco_backend.config.exceptions.InternalException;
import com.socialxchange.soco_backend.service.InfluencerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/influencer")
public class InfluencerController {

    private final InfluencerService influencerService;

    public InfluencerController(InfluencerService influencerService) {
        this.influencerService = influencerService;
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<VTransaction>> getBalance(@RequestHeader String Authorization) throws InternalException {
        User user = Utility.verifyJWT(Authorization);
        return ResponseEntity.ok(influencerService.getTransactions(user));
    }

}
