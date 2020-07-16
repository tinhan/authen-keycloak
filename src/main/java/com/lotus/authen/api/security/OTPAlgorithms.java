package com.lotus.authen.api.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class OTPAlgorithms {

    public static String geek_RefCode(int len)
    {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[len];
        secureRandom.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token).substring(0,5);
    }

    public static String OTP(int len)
    {
        StringBuilder generatedToken = new StringBuilder();
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            for (int i = 0; i < len; i++) {
                generatedToken.append(number.nextInt(9));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedToken.toString();
    }
}
