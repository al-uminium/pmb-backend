package ppm.backend.repo;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ppm.backend.model.Expense;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class MongoRepo {
    @Autowired
    private MongoTemplate mongoTemplate;

    public Document insertLedger(Document ledger) {
        return mongoTemplate.insert(ledger, "ledger");
    }

    public List<Document> getLedgerForExpense(UUID eid) {
        Criteria criteria = Criteria.where("eid").is(eid.toString());
        Query query = Query.query(criteria);
        return mongoTemplate.find(query, Document.class, "ledger");
    }

    public List<Document> getAllLedgersForExpenseGroup(List<String> eidList) {
        Query query = new Query(Criteria.where("eid").in(eidList));
        return mongoTemplate.find(query, Document.class, "ledger");
    }
}
