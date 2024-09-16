package ppm.backend.mapper;

import com.mongodb.MongoException;
import org.bson.Document;
import ppm.backend.interfaces.MongoMapper;
import ppm.backend.model.Ledger;
import ppm.backend.model.Member;

import java.util.*;

public class LedgerMapper implements MongoMapper<List<Ledger>> {
    @Override
    public List<Ledger> map(Document queryResults) throws MongoException {
        List<Ledger> ledgerList = new ArrayList<>();
        List<Document> ledgers = queryResults.getList("ledger", Document.class);
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
