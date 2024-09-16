package ppm.backend.controller;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ppm.backend.model.Expense;
import ppm.backend.model.Ledger;
import ppm.backend.service.MongoService;
import ppm.backend.service.RetrieveService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/get", method = RequestMethod.GET)
public class GetController {
    @Autowired
    private RetrieveService retrieveSvc;

    @GetMapping("/expense/{token}")
    public ResponseEntity<String> getLedger(@PathVariable String eidString) {
        UUID eid = UUID.fromString(eidString);
        Optional<Expense> expense = retrieveSvc.getExpense(eid);
        if (expense.isPresent()) {
            return ResponseEntity.ok(expense.get().toString());
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Expense not found");

    }
    
//    @GetMapping("/expense/{token}/all")
//    public ResponseEntity<String> getAllExpenses(@PathVariable String token) {
//        Optional<HashMap<String,Expense>> expenseHashMap = retrieveSvc.getExpenseDetailsForGroupFromDB(token);
//        if (expenseHashMap.isPresent()) {
//            return ResponseEntity.ok(expenseHashMap.get().toString());
//        }
//        return ResponseEntity.ok("No expenses found");
//    }

    @GetMapping("/expense/{token}/all")
    public ResponseEntity<String> getAllExpensesInGroup(@PathVariable String token) {
        Optional<List<Expense>> expenseListOpt = retrieveSvc.getAllExpensesForGroup(token);
        if (expenseListOpt.isPresent()) {
            return ResponseEntity.ok(expenseListOpt.get().toString());
        }
        return  ResponseEntity.ok("No expenses found");
    }
}
