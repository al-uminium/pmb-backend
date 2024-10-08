package ppm.backend.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

import org.springframework.stereotype.Service;
import ppm.backend.model.Expense;

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

  public Double roundToTwoDecimals(Double num) {
    BigDecimal rounded = new BigDecimal(num).setScale(2, RoundingMode.HALF_UP);
    return rounded.doubleValue();
  }
}
