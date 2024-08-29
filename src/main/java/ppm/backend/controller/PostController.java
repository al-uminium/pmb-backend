package ppm.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ppm.backend.model.Expense;

import javax.swing.GroupLayout.Group;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/api/post", method = RequestMethod.POST)
public class PostController {
  @PostMapping("/group")
  public ResponseEntity<String> createGroup(@RequestBody Group group) {
      
      return ResponseEntity.ok(" ");
  }
  
  @PostMapping("/expense/{token}")
  public ResponseEntity<String> createExpense(@RequestBody Expense expense) {
      
      return ResponseEntity.ok(" ");
  }
  

}
