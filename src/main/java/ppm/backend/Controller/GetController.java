package ppm.backend.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ppm.backend.Model.Expense;
import ppm.backend.Model.User;
import ppm.backend.Service.DataService;
import ppm.backend.Service.UtilService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(value = "/api/get", method = RequestMethod.GET)
public class GetController {
  @Autowired
  private DataService dataSvc;

  @Autowired
  private UtilService utilSvc;

  private ObjectMapper mapper = new ObjectMapper();
  
  @GetMapping("/expenditure/{path}")
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
  
}
