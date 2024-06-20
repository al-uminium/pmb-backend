package ppm.backend.Model;

import java.util.UUID;

import lombok.Data;

@Data
public class ExpenditureUser extends User {
  private UUID expenditureId; 
}
