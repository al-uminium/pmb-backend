package ppm.backend.Model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class Expenditure {
  UUID exid;
  String defaultCurrency;
  String expenditureName;
  List<User> users;
  String inviteToken;
  List<Expense> expenses;
  Instant createdDate;
  Instant updatedDate;
}
