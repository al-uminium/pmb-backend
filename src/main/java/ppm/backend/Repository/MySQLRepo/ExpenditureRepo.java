package ppm.backend.Repository.MySQLRepo;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

@Repository
public class ExpenditureRepo implements SQLQueries, SQLColumns {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  public void insertToExpenditures(UUID expid, String exName, String currency) {
    jdbcTemplate.update(INSERT_INTO_EXPENDITURES, expid.toString(), exName, currency);
  }

  // insert into Expense (expense_id, owner_id, expenditure_id, expense_name, total_cost) VALUES (?, ?, ?, ?, ?)
  public void insertToExpenses(UUID eid, UUID uid, UUID exid, String eName, Double totalCost) {
    jdbcTemplate.update(INSERT_INTO_EXPENSES, eid.toString(), uid.toString(), exid.toString(), totalCost, eName);
  }

  public void insertToExpenseUsers(UUID eid, UUID uid) {
    jdbcTemplate.update(INSERT_INTO_EXPENSE_USERS, eid.toString(), uid.toString());
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

  public SqlRowSet getExpenditureFromPath(String path) {
    return jdbcTemplate.queryForRowSet(GET_EXPENDITURE_ID_FROM_PATH, path);
  }
  
  public SqlRowSet getExpensesForExpenditure(String path) {
    return jdbcTemplate.queryForRowSet(GET_EXPENSES_FOR_EXPENDITURE, path);
  }

  public SqlRowSet getExpensesForUser(String path, UUID uid) {
    return jdbcTemplate.queryForRowSet(GET_EXPENSES_FOR_EXPENDITURE_FOR_USER, path, uid.toString());
  }

  public SqlRowSet getUsersOfExpenditure(String path) {
    return jdbcTemplate.queryForRowSet(GET_USERS_FOR_EXPENDITURE, path);
  }

  public SqlRowSet getExpenditureDetails(String path) {
    return jdbcTemplate.queryForRowSet(GET_EXPENDITURE_DETAILS, path);
  }
}
