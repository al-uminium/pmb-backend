package ppm.backend.Repository.MySQLRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class ExpenditureRepo implements SQLQueries, SQLColumns{
  @Autowired private JdbcTemplate jdbcTemplate;
}
