package ppm.backend.Repository.MySQLRepo;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class ExpenditureRepo implements SQLQueries, SQLColumns {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  public void insertToExpenditures(UUID expid, String exName, String currency) {
    jdbcTemplate.update(INSERT_INTO_EXPENDITURES, expid.toString(), exName, currency);
  }

  // insert into Expense (expense_id, owner_id, expenditure_id, expense_name, total_cost) VALUES (?, ?, ?, ?, ?)
  public void insertToExpenses(UUID eid, UUID uid, UUID exid, String eName, Integer totalCost) {
    jdbcTemplate.update(INSERT_INTO_EXPENSES, eid.toString(), uid.toString(), exid.toString(), totalCost, eName);
  }

  public void insertToUser(UUID uid, String username) {
    jdbcTemplate.update(INSERT_INTO_USER, uid.toString(), username);
  }

  public void insertToExpenditureUsers(UUID exid, UUID uid) {
    jdbcTemplate.update(INSERT_INTO_EXPENDITURE_USERS, exid.toString(), uid.toString());
  }

  public void insertToInvites(UUID exid, String inviteToken) {
    jdbcTemplate.update(INSERT_INTO_INVITES, exid.toString(), inviteToken);
  }

  
}
