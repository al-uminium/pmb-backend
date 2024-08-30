package ppm.backend.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ExpenseGroup {
  UUID gid; 
  String token;
  String name;
  List<Expense> expenses;
  List<Member> members; 
  LocalDateTime createdAt;
  Currency defaultCurrency; 
}
