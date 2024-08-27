package ppm.backend.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.Data;

@Data
public class Expense {
  UUID eid; 
  String title; 
  Currency currency;
  List<Member> members; 
  Map<Member, Integer> ledger;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
}
