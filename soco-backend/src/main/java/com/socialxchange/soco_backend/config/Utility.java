package com.socialxchange.soco_backend.config;

import com.socialxchange.soco_backend.config.dto.User;
import com.socialxchange.soco_backend.config.exceptions.InternalException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

public class Utility {

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private static final String PASSWORD_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final String JWT_ALGORITHM = "HmacSHA256";
    private static final String secret = "asdd9s9adu2901khbsd09U9us9jSIOPAJSajsjaShs091562";
    private static final Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), JWT_ALGORITHM);

    public static String hashPassword(String password) throws InternalException {
        byte[] salt = generateSalt();
        byte[] hash = null;
        try {
            hash = pbkdf2(password.toCharArray(), salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new InternalException(e.getMessage());
        }
        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
    }

    public static boolean validatePassword(String originalPassword, String storedPassword) throws InternalException {
        String[] parts = storedPassword.split(":");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] hash = Base64.getDecoder().decode(parts[1]);
        byte[] testHash = null;
        try {
            testHash = pbkdf2(originalPassword.toCharArray(), salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new InternalException(e.getMessage());
        }
        return slowEquals(hash, testHash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, Utility.ITERATIONS, Utility.KEY_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PASSWORD_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }

    public static String getJWT(Long id, String email, String userType) {
        Instant now = Instant.now();
        return Jwts.builder()
                   .claim("userType", userType)
                   .subject(email)
                   .id(id.toString())
                   .issuedAt(Date.from(now))
                   .expiration(Date.from(now.plus(5L, ChronoUnit.DAYS)))
                   .signWith(hmacKey)
                   .compact();
    }

    public static User verifyJWT(String Authorization) throws InternalException {
        try {
            String token = Authorization.split(" ")[1];
            Claims claims = Jwts.parser().verifyWith((SecretKey) hmacKey).build().parseSignedClaims(token).getPayload();
            Long id = Long.parseLong(claims.getId());
            String email = claims.getSubject();
            String userType = claims.get("userType", String.class);
            Date expDate = claims.getExpiration();
            if (!expDate.after(Date.from(Instant.now()))) {
                throw new InternalException("expired_auth_token");
            }
            User user = new User();
            user.setEmail(email);
            user.setUserType(userType);
            user.setId(id);
            return user;
        } catch (Exception e) {
            throw new InternalException("invalid_auth_token");
        }
    }
}
