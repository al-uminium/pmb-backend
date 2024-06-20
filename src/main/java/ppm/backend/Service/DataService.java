package ppm.backend.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ppm.backend.Repository.MySQLRepo.ExpenditureRepo;
import ppm.backend.Repository.mongoRepo.ExpenseRepo;

@Service
public class DataService {

  @Autowired
  private ExpenditureRepo sqlRepo;

  @Autowired
  private ExpenseRepo mongoRepo;

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
    return mapper.valueToTree(insertedUsers);

  }

  public void insertUserToExpenditure(UUID uid, UUID eid){
    sqlRepo.insertToExpenditureUsers(eid, uid);
  }

  public Document createExpenseInMongo(Document doc, UUID eid) {
    doc.append("eid", eid.toString());
    Document createdDoc = mongoRepo.insertExpense(doc);
    return createdDoc;
  }

  public void createExpenseInDB(UUID eid, UUID uid, UUID exid, String eName, Integer totalCost) {
    sqlRepo.insertToExpenses(eid, uid, exid, eName, totalCost);
    // return eid;
  }
}
