package ppm.backend.Model;

import java.util.UUID;

import lombok.Data;

@Data
public class Expenditure {
  UUID exid;
  String defaultCurrency;
  // Currency defaultCurrency;
  String expenditureName;
  // List<User> users;
  // List<Expense> expenses;
  // Instant createdDate;
}
