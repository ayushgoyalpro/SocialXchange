package com.socialxchange.soco_backend.api;

import com.socialxchange.soco_backend.config.Utility;
import com.socialxchange.soco_backend.config.database.entities.Business;
import com.socialxchange.soco_backend.config.database.entities.Influencer;
import com.socialxchange.soco_backend.config.database.repositories.BusinessRepository;
import com.socialxchange.soco_backend.config.database.repositories.InfluencerRepository;
import com.socialxchange.soco_backend.config.dto.AuthToken;
import com.socialxchange.soco_backend.config.dto.User;
import com.socialxchange.soco_backend.config.exceptions.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
public class GeneralController {

    private final InfluencerRepository influencerRepository;
    private final BusinessRepository businessRepository;

    public GeneralController(InfluencerRepository influencerRepository, BusinessRepository businessRepository) {
        this.influencerRepository = influencerRepository;
        this.businessRepository = businessRepository;
    }

    @GetMapping("/login")
    public ResponseEntity<AuthToken> login(@RequestBody User user) throws InternalException {
        log.info("Login API called: Received: {}", user.getEmail());
        Optional<Influencer> influencer = influencerRepository.findByEmail(user.getEmail());
        if (influencer.isPresent() && Utility.validatePassword(user.getPassword(), influencer.get().getPasswordHash())) {
            String jwt = Utility.getJWT(influencer.get().getId(), influencer.get().getEmail(), "influencer");
            return ResponseEntity.ok(new AuthToken(jwt));
        } else {
            Optional<Business> business = businessRepository.findByEmail(user.getEmail());
            if (business.isPresent() && Utility.validatePassword(user.getPassword(), business.get().getPasswordHash())) {
                String jwt = Utility.getJWT(business.get().getId(), business.get().getEmail(), "business");
                return ResponseEntity.ok(new AuthToken(jwt));
            } else {
                throw new InternalException("wrong_credentials");
            }
        }
    }
}
