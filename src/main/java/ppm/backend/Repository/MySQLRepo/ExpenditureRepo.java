package ppm.backend.Repository.MySQLRepo;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import ppm.backend.Model.Expenditure;

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

  public List<Expenditure> getExpenditureFromPath(String path) {
    List<Expenditure> expList = new LinkedList<>();
    SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_EXPENDITURE_ID_FROM_PATH, path);
    if (rs.next()) {
      Expenditure expenditure = new Expenditure();
      UUID exid = UUID.fromString(rs.getString(EXPENDITURE_ID));
      expenditure.setExid(exid);
      expList.add(expenditure);
    }
    return expList;
  }
  
}
