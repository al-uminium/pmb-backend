package ppm.backend.Model;

import java.util.Map;
import java.util.UUID;

public class Expense {
  User expenseOwner; 
  Integer totalCost;
  Map<User, Double> expenseSplit;
  UUID eid;
  
  public User getExpenseOwner() {
    return expenseOwner;
  }
  public void setExpenseOwner(User expenseOwner) {
    this.expenseOwner = expenseOwner;
  }
  public Integer getTotalCost() {
    return totalCost;
  }
  public void setTotalCost(Integer totalCost) {
    this.totalCost = totalCost;
  }
  public Map<User, Double> getExpenseSplit() {
    return expenseSplit;
  }
  public void setExpenseSplit(Map<User, Double> expenseSplit) {
    this.expenseSplit = expenseSplit;
  }
  public UUID getEid() {
    return eid;
  }
  public void setEid(UUID eid) {
    this.eid = eid;
  }
}
