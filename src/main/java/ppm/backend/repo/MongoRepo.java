package ppm.backend.repo;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MongoRepo {
    @Autowired
    private MongoTemplate mongoTemplate;

    public Document insertLedger(Document ledger) {
        return mongoTemplate.insert(ledger, "ledger");
    }
}
