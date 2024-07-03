package ppm.backend.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ppm.backend.Model.Expenditure;
import ppm.backend.Model.Expense;
import ppm.backend.Model.User;
import ppm.backend.Service.DataService;
import ppm.backend.Service.UtilService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping(value = "/api/get", method = RequestMethod.GET)
@CrossOrigin(origins = "http://localhost:4200")
public class GetController {
  @Autowired
  private DataService dataSvc;

  @Autowired
  private UtilService utilSvc;

  private ObjectMapper mapper = new ObjectMapper();
  
  @GetMapping("/expenditure/expenses/{path}")
  public String getExpensesForExpenditure(@PathVariable String path) {
    List<Expense> expenseList = dataSvc.getExpenseDetails(path);
    try {
      String jsonArray = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expenseList);
      return jsonArray;
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return "Failed to convert expense list to array";
    }
  }
  
  @GetMapping("/expenditure/summary/{path}&{uid}")
  public String getCostSummaryForExpenditure(@PathVariable String path, @PathVariable String uid) {
    List<User> userList = dataSvc.getExpenseSummaryOfAllUsers(path);

    Map<String, Double> calculatedCreditAndDebt = utilSvc.calcSummaryForUser(userList, UUID.fromString(uid));
    try {
      String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(calculatedCreditAndDebt);
      return jsonString;
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return "aaaa";
    }
  }

  @GetMapping("/expenditure/users/{path}")
  public String getUsersForExpenditure(@PathVariable String path) {
      List<User> userList = dataSvc.getUsersForExpenditure(path);
      
      try {
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userList);
        return jsonString;
      } catch (JsonProcessingException e) {
        e.printStackTrace();
        return "Unable to get users for %s".formatted(path);
      }
  }
  
  @GetMapping("/expenditure/{path}")
  public String getMethodName(@PathVariable String path) {
    Expenditure expenditure = dataSvc.getExpenditureDetails(path);

    try {
      String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expenditure);
      return jsonString;
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return "Unable to get expenditure";
    }
  }
  
}
