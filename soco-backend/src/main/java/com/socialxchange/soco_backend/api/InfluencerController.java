package com.socialxchange.soco_backend.api;

import com.socialxchange.soco_backend.config.Utility;
import com.socialxchange.soco_backend.config.database.entities.VTransaction;
import com.socialxchange.soco_backend.config.dto.Payment;
import com.socialxchange.soco_backend.config.dto.User;
import com.socialxchange.soco_backend.config.exceptions.InternalException;
import com.socialxchange.soco_backend.service.InfluencerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<VTransaction>> getTransactions(@RequestHeader String Authorization) throws InternalException {
        User user = Utility.verifyJWT(Authorization);
        return ResponseEntity.ok(influencerService.getTransactions(user));
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> getBalance(@RequestHeader String Authorization) throws InternalException {
        User user = Utility.verifyJWT(Authorization);
        return ResponseEntity.ok(influencerService.getBalance(user));
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> addTransaction(@RequestHeader String Authorization, @RequestBody Payment payment) throws InternalException {
        User user = Utility.verifyJWT(Authorization);
        boolean status = influencerService.makePayment(user, payment);
        if (status) {
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

}
