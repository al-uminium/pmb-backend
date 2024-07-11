package ppm.backend.Model;

import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import lombok.Data;
import ppm.backend.Repository.MySQLRepo.SQLColumns;

@Data
public class User {
  String userName;
  UUID userId;
  Double balance;
  Double accumulatedTotalCost;
  Map<String, Double> accumulatedCredit;

  public User(){}

  public User(String userName, UUID userId) {
    this.userName = userName;
    this.userId = userId;
  } 

  public User(SqlRowSet rs) {
    this.userName = rs.getString(SQLColumns.USERNAME);
    this.userId = UUID.fromString(rs.getString(SQLColumns.USER_ID));
    this.balance = rs.getDouble(SQLColumns.BALANCE);
  }

}
