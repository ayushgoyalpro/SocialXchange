package com.socialxchange.soco_backend.api;

import com.socialxchange.soco_backend.config.Utility;
import com.socialxchange.soco_backend.config.database.entities.User;
import com.socialxchange.soco_backend.config.database.repositories.UserRepository;
import com.socialxchange.soco_backend.config.dto.AuthToken;
import com.socialxchange.soco_backend.config.exceptions.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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
        if(userFound == null) {
            throw new InternalException("Wrong credentials");
        }
        else {
            try {
                if (Utility.validatePassword(user.getPassword(), userFound.getPasswordHash())) {
                    return ResponseEntity.ok(new AuthToken("TOKEN"));
                }
                else {
                    throw new InternalException("Wrong credentials");
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new InternalException(e.getMessage());
            }
        }
    }

    @PostMapping
    public User register(@RequestBody User user) throws InternalException {
        log.info("Register API called: Received: {}", user.getEmail());
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        try {
            newUser.setPasswordHash(Utility.hashPassword(user.getPassword()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new InternalException(e.getMessage());
        }
        return userRepository.save(newUser);
    }
}
