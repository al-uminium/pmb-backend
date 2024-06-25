package ppm.backend.Model;

import java.util.Map;
import java.util.UUID;



import lombok.Data;

@Data
public class User {
  String userName;
  UUID userId;
  Double accumulatedTotalCost;
  Map<String, Double> accumulatedCredit; 
}
