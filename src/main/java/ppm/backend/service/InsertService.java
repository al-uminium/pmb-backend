package ppm.backend.service;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ppm.backend.mapper.CountMapper;
import ppm.backend.model.Expense;
import ppm.backend.model.ExpenseGroup;
import ppm.backend.model.Member;
import ppm.backend.repo.SQLRepo;

@Service
public class InsertService {
  private final CountMapper countMapper = new CountMapper();
  @Autowired
  private SQLRepo repo;

  @Autowired
  private MongoService mongoSvc;

  @Autowired
  private HelperService helperSvc;

  @Transactional(rollbackFor = SQLException.class)
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
    SqlRowSet countResult = repo.getCountOfTokenInstance(token);
    Integer countCheck = countMapper.map(countResult);
    if (countCheck == 1) {
      SqlRowSet rs = repo.getGidFromToken(token);
      rs.next();
      return Optional.of(UUID.fromString(Objects.requireNonNull(rs.getString("gid"))));
    }
    return Optional.empty();
  }

  @Transactional(rollbackFor = SQLException.class)
  public Optional<Expense> createExpense(Expense expense, String token) {
    Optional<UUID> gid = getGidFromToken(token);
    if (gid.isPresent()) {
      UUID eid = UUID.randomUUID();
      expense.setEid(eid);
      try {
        repo.insertIntoExpense(expense, gid.get());
        repo.insertIntoExpenseParticipants(expense);
        Document ledger =  mongoSvc.createLedgerInMongo(gid.get(), expense);
        expense.setCreatedLedger(ledger);
        return Optional.of(expense);
      } catch (DataAccessException e) {
        throw new RuntimeException(e);
      }
    }
    return Optional.empty();
  }
}
