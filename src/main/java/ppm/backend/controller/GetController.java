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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/get", method = RequestMethod.GET)
public class GetController {
    @Autowired
    private RetrieveService retrieveSvc;

    @GetMapping("/expense/{token}")
    public ResponseEntity<String> getLedger(@PathVariable String token) {
        UUID eid = UUID.fromString(token);
        Optional<Expense> expense = retrieveSvc.getExpense(eid);
        if (expense.isPresent()) {
            return ResponseEntity.ok(expense.get().toString());
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Expense not found");
        }
    }
}
