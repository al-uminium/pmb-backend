package ppm.backend.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ppm.backend.model.Expense;
import ppm.backend.model.ExpenseGroup;
import ppm.backend.model.Ledger;
import ppm.backend.model.Member;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

@Repository
public class SQLRepo implements SQLQueries, SQLColumns{
  @Autowired
  private JdbcTemplate jdbcTemplate;

  public void insertIntoGroup(ExpenseGroup expenseGroup) throws DataAccessException {
    jdbcTemplate.update(
            INSERT_INTO_EXPENSE_GROUP,
            expenseGroup.getGid().toString(),
            expenseGroup.getName(),
            expenseGroup.getToken(),
            expenseGroup.getDefaultCurrency().toString()
    );
  }

  public SqlRowSet getGidFromToken(String token) {
    return jdbcTemplate.queryForRowSet(GET_EXPENSE_GROUP_FROM_TOKEN, token);
  }

  public void insertIntoMember(Member member) throws DataAccessException {
    jdbcTemplate.update(
            INSERT_INTO_MEMBER,
            member.getMid().toString(),
            member.getName()
    );
  }

  public void insertIntoGroupMembers(ExpenseGroup group, Member member) throws DataAccessException {
    jdbcTemplate.update(
            INSERT_INTO_GROUP_MEMBERS,
            group.getGid().toString(),
            member.getMid().toString()
    );
  }

  public void insertIntoExpense(Expense expense, UUID gid) throws DataAccessException {
    jdbcTemplate.update(
            INSERT_INTO_EXPENSE,
            expense.getEid().toString(),
            gid.toString(),
            expense.getOwner().getMid().toString(),
            expense.getTitle(),
            expense.getCurrency().toString(),
            expense.getTotalCost()
    );
  }

  public void insertIntoExpenseParticipants(Expense expense) throws DataAccessException {
    for (Ledger ledger: expense.getLedgerParticipants()) {
      jdbcTemplate.update(
              INSERT_INTO_EXPENSE_PARTICIPANTS,
              expense.getEid().toString(),
              ledger.member().getMid().toString()
      );
    }
  }

  public SqlRowSet getCountOfTokenInstance(String token) throws DataAccessException {
    return jdbcTemplate.queryForRowSet(UNIQUE_CHECK_FOR_TOKEN, token);
  }

  public SqlRowSet getExpenseDetail(UUID eid) throws DataAccessException {
    return jdbcTemplate.queryForRowSet(GET_EXPENSE_DETAILS, eid.toString());
  }

  public SqlRowSet getAllExpenseDetailsForGroup(String token) throws DataAccessException {
    return jdbcTemplate.queryForRowSet(GET_ALL_EXPENSE_DETAILS_FOR_EXPENSE_GROUP, token);
  }

  public SqlRowSet getCountOfExpensesInGroup(String token) throws DataAccessException {
    return jdbcTemplate.queryForRowSet(GET_COUNT_OF_EXPENSES_IN_GROUP, token);
  }
}
