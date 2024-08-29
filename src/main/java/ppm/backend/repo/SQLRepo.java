package ppm.backend.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ppm.backend.model.Group;
import ppm.backend.model.Member;

import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class SQLRepo implements SQLQueries, SQLColumns{
  @Autowired
  private JdbcTemplate jdbcTemplate;

  public void insertIntoGroup(Group group) {
    jdbcTemplate.update(
      INSERT_INTO_GROUP, 
      group.getGid().toString(),
      group.getName(),
      group.getToken(),
      group.getDefaultCurrency()
    );
  }

  public void insertIntoMember(Member member) {
    jdbcTemplate.update(
      INSERT_INTO_MEMBER,
      member.getMid(),
      member.getName()
    );
  }
}
