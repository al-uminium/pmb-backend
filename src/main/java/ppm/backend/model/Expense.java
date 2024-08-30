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
  Member owner;
  Integer totalCost;
  List<Member> participants; 
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
  String attachmentUrl;
  // using string to avoid custom serializer/deserializers
  // string will be referring to mid of members.
  Map<String, Integer> ledger;
}
