package ppm.backend.Service;

import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppm.backend.Repository.MySQLRepo.ExpenditureRepo;
import ppm.backend.Repository.mongoRepo.ExpenseRepo;

@Service
public class DataService {

  @Autowired
  private ExpenditureRepo sqlRepo;

  @Autowired
  private ExpenseRepo mongoRepo;

  public UUID createExpenditure(String name, String curr) {
    UUID exid = UUID.randomUUID();
    sqlRepo.insertToExpenditures(exid, name, curr);
    return exid;
  }

  public UUID createUser(String username) {
    UUID uid = UUID.randomUUID();
    sqlRepo.insertToUser(uid, username);
    return uid;
  }

  public UUID createExpenseInMongo(Document doc) {
    UUID eid = UUID.randomUUID();
    doc.append("eid", eid.toString());
    Document createdDoc = mongoRepo.insertExpense(doc);
    return eid;
  }
  // insert into Expense (expense_id, owner_id, expenditure_id, expense_name, mongo_split_id, total_cost) VALUES (?, ?, ?, ?, ?, ?)
  public UUID createExpenseInDB(UUID eid, UUID uid, UUID exid, String eName, Integer totalCost) {
    sqlRepo.insertToExpenses(eid, uid, exid, eName, totalCost);;
    return eid;
  }
}
