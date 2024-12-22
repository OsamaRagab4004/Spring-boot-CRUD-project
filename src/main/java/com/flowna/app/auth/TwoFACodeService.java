package com.flowna.app.auth;

import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.Instant;

@Service
public class TwoFACodeService {

    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789#$^&*()_+-";
    private static final SecureRandom RANDOM = new SecureRandom();

    // Method to generate a random 16-character alphanumeric 2FA code
    public String generate2FACode() {
        StringBuilder code = new StringBuilder(30);
        for (int i = 0; i < 30; i++) {
            int index = RANDOM.nextInt(ALPHANUMERIC.length());
            code.append(ALPHANUMERIC.charAt(index));
        }
        return code.toString();
    }


    public long getCurrentTimeSeconds() {
        return Instant.now().getEpochSecond();
    }

    public long extractSecondsFromCode(String code) {
        String secondPart = code.substring(30);
        return Long.parseLong(secondPart);
    }

    public long CalculateDifference(String code) {
        long currentTimeInSeconds = getCurrentTimeSeconds();
        long extractedSeconds = extractSecondsFromCode(code);
        return currentTimeInSeconds - extractedSeconds;
    }



}