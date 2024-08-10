package com.socialxchange.soco_backend.api;

import com.socialxchange.soco_backend.config.Utility;
import com.socialxchange.soco_backend.config.database.entities.User;
import com.socialxchange.soco_backend.config.database.repositories.UserRepository;
import com.socialxchange.soco_backend.config.dto.AuthToken;
import com.socialxchange.soco_backend.config.exceptions.InternalException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<AuthToken> login(@RequestBody User user) throws InternalException {
        log.info("Login API called: Received: {}", user.getEmail());
        User userFound = userRepository.findByEmail(user.getEmail());
        if (userFound == null) {
            throw new InternalException("Wrong credentials");
        } else {
            if (Utility.validatePassword(user.getPassword(), userFound.getPasswordHash())) {
                String jwt = Utility.getJWT(userFound.getId(), userFound.getEmail());
                return ResponseEntity.ok(new AuthToken(jwt));
            } else {
                throw new InternalException("Wrong credentials");
            }
        }
    }

    @PostMapping
    public User register(@RequestBody User user) throws InternalException {
        log.info("Register API called: Received: {}", user.getEmail());
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPasswordHash(Utility.hashPassword(user.getPassword()));
        return userRepository.save(newUser);
    }
}
