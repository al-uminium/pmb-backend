package ppm.backend.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class HelperService {

  public String generateInviteToken(int length) {
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    SecureRandom rand = new SecureRandom();
    StringBuilder result = new StringBuilder(length);
    int charactersLength = chars.length();
    for (int i = 0; i < length; i++) {
        int randomIndex = rand.nextInt(charactersLength);  
        result.append(chars.charAt(randomIndex));     
    }
    return result.toString();
  }
}
