package ppm.backend.Model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.Data;

@Data
public class Expense {
  String expenseName;
  User expenseOwner;
  UUID expenseOwnerID;
  Double totalCost;
  Map<String, Double> expenseSplit;
  List<User> usersInvolved;
  UUID exid;
  UUID eid;

  public void setExpenseOwnerID(String expenseOwnerID) {
    this.expenseOwnerID = UUID.fromString(expenseOwnerID);
  }

  public void setExpenseOwnerID(UUID expenseOwnerID) {
    this.expenseOwnerID = expenseOwnerID;
  }

}
