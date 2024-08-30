package ppm.backend.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import ppm.backend.model.ExpenseGroup;
import ppm.backend.model.Member;

import org.springframework.jdbc.core.JdbcTemplate;

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

  public void insertIntoMember(Member member) throws DataAccessException {
    jdbcTemplate.update(
      INSERT_INTO_MEMBER,
      member.getMid().toString(),
      member.getName()
    );
  }
}
