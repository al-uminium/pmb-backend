package ppm.backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ppm.backend.model.ExpenseGroup;
import ppm.backend.model.Member;
import ppm.backend.repo.SQLRepo;

@Service
public class DataService {
  @Autowired
  private SQLRepo repo;
  @Autowired
  private HelperService helperSvc;

  public ExpenseGroup createGroup(ExpenseGroup expenseGroup) {
    UUID gid = UUID.randomUUID();
    String token = helperSvc.generateInviteToken(36);
    expenseGroup.setGid(gid);
    expenseGroup.setToken(token);
    try {
      for (Member m: expenseGroup.getMembers()) {
        UUID mid = createMember(m);
        m.setMid(mid);
      }
      repo.insertIntoGroup(expenseGroup);
      return expenseGroup;
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public UUID createMember(Member member) {
    UUID mid = UUID.randomUUID();
    member.setMid(mid);
    System.out.println(member);
    try {
      repo.insertIntoMember(member);
      return mid;
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
