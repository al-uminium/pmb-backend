package ppm.backend.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import ppm.backend.Model.Expense;
import ppm.backend.Model.User;

@Service
public class UtilService {

  // public Map<User, Double> calcCostIncurredPerUser(List<Expense> expenses) {
  //   Map<User, Double> costMap = expenses.stream()
  //       .flatMap(expense -> expense.getExpenseSplit().entrySet().stream())
  //       .collect(Collectors.toMap(
  //           Map.Entry::getKey,
  //           Map.Entry::getValue,
  //           Double::sum));

  //   return costMap;
  // }

  public Expense convertJsonToExpense(JsonNode expenseJson) {
    Expense expense = new Expense(); 
    String expenseName = expenseJson.get("expenseName").asText();
    UUID expenseOwnerId = UUID.fromString(expenseJson.get("expenseOwnerId").toString().replaceAll("\"", ""));
    UUID exid = UUID.fromString(expenseJson.get("exid").toString().replaceAll("\"", ""));
    Double totalCost = expenseJson.get("totalCost").asDouble();

    expense.setExpenseName(expenseName);
    expense.setExpenseOwnerID(expenseOwnerId);
    expense.setExid(exid);
    expense.setTotalCost(totalCost);
    return expense;
  }

  public List<String> getListOfUsers(JsonNode data) {
    List<String> userList = new ArrayList<>(); 

    for (JsonNode user : data) {
      userList.add(user.textValue());
    }

    return userList;
  }

  public UUID getExpenditureID(List<Expense> expList) {
    return expList.getFirst().getExid();
  }

  public Map<String, Double> convertDocumentToMap(Document doc) {
    Map<String, Double> res = new HashMap<>();
    for(Map.Entry<String,Object> entry : doc.entrySet()) {
      if (entry.getValue() instanceof Double) {
        res.put(entry.getKey(), (Double) entry.getValue());
      }
    }
    return res;
  }
}
