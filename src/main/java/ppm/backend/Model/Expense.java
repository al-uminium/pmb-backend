package ppm.backend.Model;

import java.util.Map;
import java.util.UUID;

public class Expense {
  User expenseOwner; 
  Integer totalCost;
  Map<User, Double> expenseSplit;
  UUID eid;
}
