package ppm.backend.mapper;

import com.mongodb.MongoException;
import org.bson.Document;
import ppm.backend.model.Ledger;
import ppm.backend.model.Member;

import java.util.*;

public class LedgerMapper implements MongoMapper<List<Ledger>> {
    @Override
    public List<Ledger> map(List<Document> queryResults) throws MongoException {
        if (queryResults.size() > 1) {
            return null;
        } else {
            List<Ledger> ledgerList = new ArrayList<>();
            List<Document> ledgers = queryResults.getFirst().getList("ledger", Document.class);
            for (Document l: ledgers) {
                Member member = new Member();
                member.setMid(UUID.fromString(l.getString("mid")));
                member.setName(l.getString("name"));
                Double balance = l.getDouble("balance");
                ledgerList.add(new Ledger(member,balance));
            }
            return ledgerList;
        }
    }

}
