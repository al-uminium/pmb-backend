package ppm.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ppm.backend.model.Expense;

import org.springframework.http.ResponseEntity;
import ppm.backend.model.ExpenseGroup;
import ppm.backend.service.InsertService;

import java.util.Optional;


@RestController
@RequestMapping(value = "/api/post", method = RequestMethod.POST)
public class PostController {
    @Autowired
    private InsertService dataSvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/group")
    public ResponseEntity<String> createGroup(@RequestBody ExpenseGroup expenseGroup) {
        ExpenseGroup createdExpenseGroup = dataSvc.createGroup(expenseGroup);
        try {
            return ResponseEntity.ok(mapper.writeValueAsString(createdExpenseGroup));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/expense/{token}")
    public ResponseEntity<String> createExpense(@RequestBody Expense expense, @PathVariable String token) {
        Optional<Expense> opt = dataSvc.createExpense(expense, token);
        if (opt.isPresent()) {
            try {
                return ResponseEntity.ok(mapper.writeValueAsString(opt.get()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }
}
