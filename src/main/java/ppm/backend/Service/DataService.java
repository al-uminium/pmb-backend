package ppm.backend.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ppm.backend.Model.Expenditure;
import ppm.backend.Model.Expense;
import ppm.backend.Model.User;
import ppm.backend.Repository.MySQLRepo.ExpenditureRepo;
import ppm.backend.Repository.MySQLRepo.SQLColumns;
import ppm.backend.Repository.mongoRepo.ExpenseRepo;

@Service
public class DataService implements SQLColumns{

  @Autowired
  private ExpenditureRepo sqlRepo;

  @Autowired
  private ExpenseRepo mongoRepo;

  @Autowired
  private UtilService utilSvc;

  private ObjectMapper mapper = new ObjectMapper();

  public UUID createExpenditure(String name, String curr) {
    UUID exid = UUID.randomUUID();
    sqlRepo.insertToExpenditures(exid, name, curr);
    return exid;
  }

  public JsonNode createUser(String username) {
    UUID uid = UUID.randomUUID();
    sqlRepo.insertToUser(uid, username);
    Map<String, UUID> insertedUser = new HashMap<>();
    insertedUser.put(username, uid);
    return mapper.valueToTree(insertedUser);
  }

  public JsonNode createUsers(List<String> users) {
    Map<String, UUID> insertedUsers = new HashMap<>();
    for (String user : users) {
      UUID userId = UUID.randomUUID();
      sqlRepo.insertToUser(userId, user);
      insertedUsers.put(user, userId);
    }
    System.out.println(insertedUsers.toString());
    return mapper.valueToTree(insertedUsers);

  }

  public void insertUserToExpenditure(UUID uid, UUID eid){
    sqlRepo.insertToExpenditureUsers(eid, uid);
  }

  public Document createExpenseInMongo(Map<String, Double> map, UUID eid, UUID exid) {
    Document doc = new Document(map);
    doc.append("eid", eid.toString());
    doc.append("exid", exid.toString());
    Document createdDoc = mongoRepo.insertExpense(doc);
    return createdDoc;
  }

  public void createExpenseInDB(UUID eid, UUID uid, UUID exid, String eName, Double totalCost) {
    sqlRepo.insertToExpenses(eid, uid, exid, eName, totalCost);
    // return eid;
  }

  @Transactional(rollbackFor = SQLException.class)
  public void initializeNewExpenditure(String expenditureName, String currency, List<String> users, String inviteToken) throws SQLException {
    UUID eid = createExpenditure(expenditureName, currency);
    JsonNode createdUsers = createUsers(users);
    for (JsonNode user : createdUsers) {
      // System.out.println("printing... " + user.asText());
      insertUserToExpenditure(UUID.fromString(user.asText()), eid);
    }
    sqlRepo.insertToInvites(eid, inviteToken);
  }

  public UUID getExpenditureFromPath(String path) {
    SqlRowSet rs = sqlRepo.getExpenditureFromPath(path);
    if (rs.next()) {
      UUID exid = UUID.fromString(rs.getString(EXPENDITURE_ID));
      return exid;
    }
    return null;
  }

  public List<Expense> getExpenseDetails(String path){
    SqlRowSet rs = sqlRepo.getExpensesForExpenditure(path);
    List<Expense> expenseList = convertRowSetToExpenseList(rs);
    return expenseList;
  }

  public List<User> getExpenseSummaryOfAllUsers(String path) {
    List<User> userList = getUsersForExpenditure(path);
    
    for (User user : userList) {
      SqlRowSet rs = sqlRepo.getExpensesForUser(path, user.getUserId());
      List<Expense> expenseList = convertRowSetToExpenseList(rs);
      Double accTotalCost = utilSvc.calcAccumulatedTotalCost(expenseList);
      Map<String, Double> accumulatedCredit = utilSvc.calcCostIncurredPerUser(expenseList);
      user.setAccumulatedCredit(accumulatedCredit);
      user.setAccumulatedTotalCost(accTotalCost);
    }
    return userList;
  }

  public List<User> getUsersForExpenditure(String path) {
    List<User> usersList = new ArrayList<>();
    SqlRowSet rs = sqlRepo.getUsersOfExpenditure(path);

    while(rs.next()) {
      UUID uid = UUID.fromString(rs.getString(USER_ID));
      String userName = rs.getString(USERNAME);
      User user = new User();
      user.setUserId(uid);
      user.setUserName(userName);
      usersList.add(user);
    }

    return usersList;
  }


  // Not putting it under Expense bc there's a mongo call inside ):
  public List<Expense> convertRowSetToExpenseList(SqlRowSet rs) {
    List<Expense> expenseList = new LinkedList<>();

    while (rs.next()) {
      UUID eid = UUID.fromString(rs.getString(EXPENSE_ID));
      UUID ownerId = UUID.fromString(rs.getString(EXPENSE_OWNER_ID));
      String expenseName = rs.getString(EXPENSE_NAME);
      Double totalCost = rs.getDouble(TOTAL_COST);
      List<Document> expenseSplitList = mongoRepo.getExpense(eid);
      Expense exp = new Expense();
      if (expenseSplitList.size() > 0) {
        Map<String, Double> expenseSplit = utilSvc.convertDocumentToMap(expenseSplitList.getFirst());
        exp.setEid(eid);
        exp.setTotalCost(totalCost);
        exp.setExpenseOwnerID(ownerId);
        exp.setExpenseName(expenseName);
        exp.setExpenseSplit(expenseSplit);
        expenseList.add(exp);
      }
      System.out.println("Sanity check: " + exp.toString());
    }

    return expenseList;
  } 
}
