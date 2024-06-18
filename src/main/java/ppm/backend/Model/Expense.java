package ppm.backend.Model;

import java.util.Map;
import java.util.UUID;

import lombok.Data;

@Data
public class Expense {
  User expenseOwner; 
  Integer totalCost;
  Map<User, Double> expenseSplit;
  UUID expId;
  UUID eid;
}
