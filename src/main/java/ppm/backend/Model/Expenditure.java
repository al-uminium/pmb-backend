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
  List<String> users; // used for when creating a new expenditure
  String inviteToken;
  List<Expense> expenses;
  List<User> expenditureUsers; // used for when getting expenditure details
  Instant createdDate;
  Instant updatedDate;
}
