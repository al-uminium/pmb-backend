package ppm.backend.Model;

import java.util.UUID;

import lombok.Data;

@Data
public class User {
  String userName; 
  Boolean isRegistered; 
  UUID registeredUUID;
}
