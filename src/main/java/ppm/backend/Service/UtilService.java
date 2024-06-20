package ppm.backend.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import ppm.backend.Model.Expense;
import ppm.backend.Model.User;

@Service
public class UtilService {

  public Map<User, Double> calcCostIncurredPerUser(List<Expense> expenses) {
    Map<User, Double> costMap = expenses.stream()
        .flatMap(expense -> expense.getExpenseSplit().entrySet().stream())
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            Double::sum));

    return costMap;
  }

  public Expense convertJsonToExpense(JsonNode expenseJson) {
    Expense expense = new Expense(); 
    String expenseName = expenseJson.get("expenseName").asText();
    UUID expenseOwnerId = UUID.fromString(expenseJson.get("expenseOwnerId").toString().replaceAll("\"", ""));
    UUID exid = UUID.fromString(expenseJson.get("exid").toString().replaceAll("\"", ""));
    Integer totalCost = expenseJson.get("totalCost").asInt();

    expense.setExpenseName(expenseName);
    expense.setExpenseOwnerID(expenseOwnerId);
    expense.setExid(exid);
    expense.setTotalCost(totalCost);
    return expense;
  }
}
