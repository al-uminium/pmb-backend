package ppm.backend.model;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class User {
  String username; 
  UUID uid;
  String email;
  List<Group> groups;
}
