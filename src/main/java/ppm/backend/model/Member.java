package ppm.backend.model;

import java.util.UUID;

import lombok.Data;

@Data
public class Member {
  UUID mid;
  UUID uid; 
  String name;
}
