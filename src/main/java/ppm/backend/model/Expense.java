package ppm.backend.model;

import java.time.LocalDateTime;
import java.util.List;
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
  List<Ledger> ledgerParticipants;
  Document createdLedger;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
  String attachmentUrl;

  public Expense() {}

  public Expense(UUID eid, String title, Currency currency, Member owner, Double totalCost, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.eid = eid;
    this.title = title;
    this.currency = currency;
    this.owner = owner;
    this.totalCost = totalCost;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
