package com.socialxchange.soco_backend.api;

import com.socialxchange.soco_backend.config.Utility;
import com.socialxchange.soco_backend.config.database.entities.Business;
import com.socialxchange.soco_backend.config.database.entities.Influencer;
import com.socialxchange.soco_backend.config.database.repositories.BusinessRepository;
import com.socialxchange.soco_backend.config.database.repositories.InfluencerRepository;
import com.socialxchange.soco_backend.config.exceptions.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final InfluencerRepository influencerRepository;
    private final BusinessRepository businessRepository;

    public AdminController(InfluencerRepository influencerRepository, BusinessRepository businessRepository) {
        this.influencerRepository = influencerRepository;
        this.businessRepository = businessRepository;
    }

    @PostMapping("/influencer")
    public Influencer registerInfluencer(@RequestBody Influencer influencer) {
        return influencerRepository.save(influencer);
    }

    @PatchMapping("/influencer/{id}")
    public ResponseEntity<Influencer> updateInfluencer(@PathVariable Long id, @RequestBody Map<String, Object> updates) throws InternalException {
        Optional<Influencer> optionalInfluencer = influencerRepository.findById(id);
        if (optionalInfluencer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Influencer influencer = optionalInfluencer.get();
        updates.forEach((key, value) -> {
            try {
                if (key.equals("password")) {
                    value = Utility.hashPassword((String) value);
                    key = "passwordHash";
                }
                Field field = Influencer.class.getDeclaredField(key);
                field.setAccessible(true);
                field.set(influencer, value);
            } catch (NoSuchFieldException | IllegalAccessException | InternalException e) {
                log.error(e.getMessage());
            }
        });
        Influencer updatedInfluencer = influencerRepository.save(influencer);
        return ResponseEntity.ok(updatedInfluencer);
    }

    @PostMapping("/business")
    public Business registerBusiness(@RequestBody Business business) {;
        Business savedBusiness = businessRepository.save(business);
        savedBusiness.setQr(Base64.getEncoder().encodeToString(("business:" + savedBusiness.getId()).getBytes()));
        return businessRepository.save(savedBusiness);
    }

    @PatchMapping("/business/{id}")
    public ResponseEntity<Business> updateBusiness(@PathVariable Long id, @RequestBody Map<String, Object> updates) throws InternalException {
        Optional<Business> optionalBusiness = businessRepository.findById(id);
        if (optionalBusiness.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Business business = optionalBusiness.get();
        updates.forEach((key, value) -> {
            try {
                if (key.equals("password")) {
                    value = Utility.hashPassword((String) value);
                    key = "passwordHash";
                }
                Field field = Business.class.getDeclaredField(key);
                field.setAccessible(true);
                field.set(business, value);
            } catch (NoSuchFieldException | IllegalAccessException | InternalException e) {
                log.error(e.getMessage());
            }
        });
        Business updatedBusiness = businessRepository.save(business);
        return ResponseEntity.ok(updatedBusiness);
    }
}
