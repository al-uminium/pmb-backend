package ppm.backend.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.Data;
import org.bson.Document;

@Data
public class Expense {
  UUID eid;
  String title; 
  Currency currency;
  Member owner;
  Double totalCost;
  List<Ledger> participants;
  Document createdLedger;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
  String attachmentUrl;
}
