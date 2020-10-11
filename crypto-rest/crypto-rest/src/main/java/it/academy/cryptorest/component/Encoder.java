package it.academy.cryptorest.component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.logging.Logger;

@Component
@Getter
@Setter
public class Encoder {
    private static final Logger log = Logger.getLogger(Encoder.class.getName());
    private static final String CIPHER_ALGORITHM = "AES";



        public String encodeMessage (String secretKey, String msg) {
            String result = "";
            try {
              SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), CIPHER_ALGORITHM);
              Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
              cipher.init(Cipher.ENCRYPT_MODE, key);
              byte[] encryptAes = cipher.doFinal(msg.getBytes());
              result = Base64.getUrlEncoder().encodeToString(encryptAes);
            } catch (GeneralSecurityException e) {
              log.warning(e.getMessage());
          }
          return  result;
        }


        public String decodeMessage(String secretKey, String msg) {
            String result = "";
            try {
                SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(),CIPHER_ALGORITHM);
                Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE,key);

                byte[] decryptBase64 = Base64.getUrlDecoder().decode(msg);
                byte[] decryptResultBytes = cipher.doFinal(decryptBase64);
                result = new String(decryptResultBytes);

            } catch (GeneralSecurityException e) {
                log.warning(e.getMessage());
            }
            return result;
        }

}
