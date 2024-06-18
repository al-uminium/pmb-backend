package ppm.backend.Model;

import java.util.Map;
import java.util.UUID;

import lombok.Data;

@Data
public class Expense {
  String expenseName;
  UUID expenseOwnerID;
  Integer totalCost;
  Map<User, Double> expenseSplit;
  UUID exid;
  UUID eid;

  public void setExpenseOwnerID(String expenseOwnerID) {
    this.expenseOwnerID = UUID.fromString(expenseOwnerID);
  }

}
