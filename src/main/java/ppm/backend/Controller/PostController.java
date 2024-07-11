package ppm.backend.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

  private ObjectMapper mapper = new ObjectMapper();

  @PostMapping(value = "/initializeexpenditure")
  public ResponseEntity<String> createExpenditure(@RequestBody Expenditure expenditure) {
    System.out.println(expenditure.toString());
    try {
      System.out.println(expenditure);
      dataSvc.initializeNewExpenditure(expenditure.getExpenditureName(),
          expenditure.getDefaultCurrency(),
          expenditure.getUsers(),
          expenditure.getInviteToken());
      return ResponseEntity.ok(mapper.writeValueAsString("Expenditure created"));
    } catch (SQLException e) {
      e.printStackTrace();
      ResponseEntity.badRequest().body("Failed to create expenditure");
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      System.out.println("Error parsing JSON");
    }
    return null;

  }
  
  @PostMapping("/expense/{path}")
  public ResponseEntity<String> createExpense(@RequestBody Expense expense, @PathVariable String path) throws JsonProcessingException {
    System.out.println(">>> Printing expense received... "+ expense.toString());
    UUID exid = dataSvc.getExpenditureFromPath(path);
    UUID eid = UUID.randomUUID();
    expense.setEid(eid);
    expense.setExid(exid);
    dataSvc.createExpenseInDB(expense);
    dataSvc.createExpenseInMongo(expense.getExpenseSplit(), eid, exid);
    return ResponseEntity.ok(mapper.writeValueAsString("Expense created"));
  }
  
}

// Printing expense received... 
// Expense(expenseName=Repayment, expenseOwner=User(userName=Alan, userId=0170ace8-764b-4fec-a389-4542c6025711, balance=-56.67, accumulatedTotalCost=null, accumulatedCredit=null), 
// totalCost=1.5, expenseSplit={Alan=1.5, Forrest=0.0}, 
// usersInvolved=[User(userName=Alan, userId=0170ace8-764b-4fec-a389-4542c6025711, balance=-56.67, accumulatedTotalCost=null, accumulatedCredit=null), 
// User(userName=Forrest, userId=bedea353-4484-4480-86b5-ab558bb9b057, balance=-71.67, accumulatedTotalCost=null, accumulatedCredit=null)], exid=null, eid=null)


// >>> Printing expense received... 
// Expense(expenseName=Testing, expenseOwner=User(userName=Forrest, userId=bedea353-4484-4480-86b5-ab558bb9b057, balance=null, accumulatedTotalCost=null, accumulatedCredit=null), 
// totalCost=10.5, expenseSplit={Alan=3.5, Dwayne=3.5, Forrest=3.5}, 
// usersInvolved=[User(userName=Alan, userId=0170ace8-764b-4fec-a389-4542c6025711, balance=null, accumulatedTotalCost=null, accumulatedCredit=null), 
// User(userName=Dwayne, userId=a269b322-cab6-45b4-ab1d-418dd4c1afe4, balance=null, accumulatedTotalCost=null, accumulatedCredit=null)], exid=null, eid=null)