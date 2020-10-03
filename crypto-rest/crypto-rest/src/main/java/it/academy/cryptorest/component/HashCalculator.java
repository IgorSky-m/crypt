package it.academy.cryptorest.component;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class HashCalculator {

    public String createSHA3Hash(String str){
        return generateHash(str,"SHA3-256");
    }
    
    
    public String createSHA1Hash(String str) {
            return generateHash(str,"SHA-1");
        
    }


    private String generateHash(String msg, String hashAlg) {
        byte[] stringBytes = msg.getBytes(StandardCharsets.UTF_8);
        MessageDigest digest;
        String encoded = null;
        try {
            digest = MessageDigest.getInstance(hashAlg);
            byte[] byteHash = digest.digest(stringBytes);
            encoded = Base64.getUrlEncoder().encodeToString(byteHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encoded;

    }



}
