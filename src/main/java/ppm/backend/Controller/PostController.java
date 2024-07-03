package ppm.backend.Controller;

import org.springframework.web.bind.annotation.RestController;

import ppm.backend.Model.Expenditure;
import ppm.backend.Model.Expense;
import ppm.backend.Service.DataService;

import java.sql.SQLException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api/post", method = RequestMethod.POST)
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

  @Autowired
  private DataService dataSvc;

  @PostMapping(value = "/initializeexpenditure", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> createExpenditure(@RequestBody Expenditure expenditure) {
    try {
      System.out.println(expenditure.toString());
      dataSvc.initializeNewExpenditure(expenditure.getExpenditureName(),
          expenditure.getDefaultCurrency(),
          expenditure.getUsers(),
          expenditure.getInviteToken());
      return ResponseEntity.ok("Expenditure created");
    } catch (SQLException e) {
      e.printStackTrace();
      ResponseEntity.badRequest().body("Failed to create expenditure");
    }
    return null;

  }
  
  @PostMapping("/expense/{path}")
  public ResponseEntity<String> createExpense(@RequestBody Expense expense, @PathVariable String path) {
      UUID exid = dataSvc.getExpenditureFromPath(path);
      System.out.println(expense.getExpenseOwnerID());
      UUID eid = UUID.randomUUID();
      dataSvc.createExpenseInDB(eid, expense.getExpenseOwnerID(), exid, expense.getExpenseName(), expense.getTotalCost());
      dataSvc.createExpenseInMongo(expense.getExpenseSplit(), eid, exid);
      return ResponseEntity.ok(expense.toString());
  }
  
}
