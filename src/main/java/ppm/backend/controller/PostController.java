package ppm.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ppm.backend.model.Expense;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ppm.backend.model.ExpenseGroup;
import ppm.backend.service.DataService;


@RestController
@RequestMapping(value = "/api/post", method = RequestMethod.POST)
public class PostController {
    @Autowired
    private DataService dataSvc;

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
    public ResponseEntity<String> createExpense(@RequestBody Expense expense) {

        return ResponseEntity.ok(" ");
    }
  

}
