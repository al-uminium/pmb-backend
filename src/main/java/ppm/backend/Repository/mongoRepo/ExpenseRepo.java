package ppm.backend.Repository.mongoRepo;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ExpenseRepo {
  @Autowired
  private MongoTemplate mongoTemplate; 

  public Document insertExpense(Document expense) {
    return mongoTemplate.insert(expense, "expense");
  }
}
