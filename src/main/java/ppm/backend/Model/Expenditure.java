package ppm.backend.Model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Expenditure {
  List<User> users; 
  List<Expense> expenses;
  UUID exid;
  Instant createdDate;
  Currency defaultCurrency;
  Boolean isMarkedForDelete; //not sure if this param is needed
}
