// package ppm.backend.Controller;

// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.JsonMappingException;
// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;

// import ppm.backend.Model.Expenditure;
// import ppm.backend.Model.ExpenditureUser;
// import ppm.backend.Model.Expense;
// import ppm.backend.Model.User;
// import ppm.backend.Service.DataService;
// import ppm.backend.Service.UtilService;

// import java.util.List;
// import java.util.Map;
// import java.util.UUID;

// import org.bson.Document;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;

// @RestController
// @RequestMapping
// public class ExpenditureController {

//   @Autowired
//   DataService dataSvc;
//   @Autowired
//   UtilService utilSvc;

//   @PostMapping("/api/post/expenditure")
//   // expecting the following format
//   // {
//   // "expenditureName": "test",
//   // "defaultCurrency": "SGD"
//   // }
//   public ResponseEntity<UUID> postExpenditure(@RequestBody Expenditure expenditure) {
//     String exName = expenditure.getExpenditureName();
//     String curr = expenditure.getDefaultCurrency();
//     UUID exid = dataSvc.createExpenditure(exName, curr);
//     return ResponseEntity.ok(exid);
//   }

//   @PostMapping("/api/post/expenditureuser")
//   // {
//   // "userName": "testusername",
//   // "expenditureId": "a8516f9b-f15f-416d-88ac-b27af1dbae6f"
//   // } 
//   public ResponseEntity<JsonNode> postExpenditureUser(@RequestBody ExpenditureUser user) {
//     JsonNode createdUser = dataSvc.createUser(user.getUserName());
//     UUID uid = UUID.fromString(createdUser.get(user.getUserName()).asText().replaceAll("\"", ""));
//     System.out.println(">>> UID: " + uid);
//     System.out.println(">>> EID: " + user.getExpenditureId());
//     dataSvc.insertUserToExpenditure(uid, user.getExpenditureId());
//     return ResponseEntity.ok(createdUser);
//   }

//   @PostMapping("/api/post/expenditure/expense")
//   // {
//   // "expenseName": "chimken chop",
//   // "expenseOwnerId": "bf7b74fa-c7e4-4d5a-91e4-036bdeb94e9a",
//   // "exid": "70ecadd8-8ed7-4645-a8da-86cf4e73af24",
//   // "totalCost": 50,
//   // "expenseSplit": {
//   // "abc": 10,
//   // "def": 40
//   // }
//   // }
//   public ResponseEntity<UUID> postExpense(@RequestBody String expense) {
//     JsonNode expenseJson;
//     ObjectMapper mapper = new ObjectMapper();
//     UUID eid = UUID.randomUUID();
//     try {
//       expenseJson = mapper.readTree(expense);
//       Expense e = utilSvc.convertJsonToExpense(expenseJson);
//       Map<String, Integer> expenseSplit = mapper.convertValue(expenseJson.get("expenseSplit"), Map.class);
//       Document split = new Document(expenseSplit);
//       Document createdDocument = dataSvc.createExpenseInMongo(split, eid);

//       if (createdDocument.isEmpty()) {
//         return ResponseEntity.noContent().build();
//       }

//       dataSvc.createExpenseInDB(eid, e.getExpenseOwnerID(), e.getExid(), e.getExpenseName(), e.getTotalCost());
//       return ResponseEntity.ok(eid);
//     } catch (JsonMappingException e) {
//       e.printStackTrace();
//       return ResponseEntity.badRequest().build();
//     } catch (JsonProcessingException e) {
//       e.printStackTrace();
//       return ResponseEntity.badRequest().build();
//     }
//   }
  
// }
