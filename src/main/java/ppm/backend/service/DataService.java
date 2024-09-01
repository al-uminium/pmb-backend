package ppm.backend.service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ppm.backend.model.Expense;
import ppm.backend.model.ExpenseGroup;
import ppm.backend.model.Member;
import ppm.backend.repo.SQLRepo;

@Service
public class DataService {
  @Autowired
  private SQLRepo repo;
  @Autowired
  private HelperService helperSvc;

  @Transactional
  public ExpenseGroup createGroup(ExpenseGroup group) {
    UUID gid = UUID.randomUUID();
    String token = helperSvc.generateInviteToken(36);
    group.setGid(gid);
    group.setToken(token);
    try {
      repo.insertIntoGroup(group);
      for (Member m: group.getMembers()) {
        UUID mid = UUID.randomUUID();
        m.setMid(mid);
        repo.insertIntoMember(m);
        repo.insertIntoGroupMembers(group, m);
      }
      return group;
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private Optional<UUID> getGidFromToken(String token) {
    SqlRowSet count_rs = repo.getCountOfTokenInstance(token);
    if (count_rs.next()) {
      if (count_rs.getInt("count") == 1) {
        SqlRowSet rs = repo.getGidFromToken(token);
        rs.next();
        return Optional.of(UUID.fromString(Objects.requireNonNull(rs.getString("gid"))));
      }
    }
    return Optional.empty();
  }

  public Optional<Expense> createExpense(Expense expense, String token) {
    UUID eid = UUID.randomUUID();
    Optional<UUID> opt = getGidFromToken(token);
    if (opt.isPresent()) {
      UUID gid = opt.get();
      expense.setEid(eid);
      try {
        repo.insertIntoExpense(expense, gid);
        return Optional.of(expense);
      } catch (DataAccessException e) {
        throw new RuntimeException(e);
      }
    }
    return Optional.empty();
  }
}
