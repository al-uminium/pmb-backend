package ppm.backend.Model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.Data;

@Data
public class Expense {
  String expenseName;
  UUID expenseOwnerID;
  Integer totalCost;
  Map<String, Double> expenseSplit;
  UUID exid;
  UUID eid;

  public void setExpenseOwnerID(String expenseOwnerID) {
    this.expenseOwnerID = UUID.fromString(expenseOwnerID);
  }

  public void setExpenseOwnerID(UUID expenseOwnerID) {
    this.expenseOwnerID = expenseOwnerID;
  }

}
