package ppm.backend.Model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class Expenditure {
  List<User> users; 
  List<Expense> expenses;
  UUID exid;
  Instant createdDate;
  Currency defaultCurrency;
}
