package ppm.backend.service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppm.backend.model.Expense;
import ppm.backend.model.Ledger;
import ppm.backend.repo.MongoRepo;

import javax.print.Doc;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MongoService {
    @Autowired
    private MongoRepo repo;

    @Autowired
    private HelperService svc;
    public Document createLedger(UUID gid, Expense expense) {
        List<Document> participants = expense.getParticipants().stream()
                .map(ledgerInfo -> new Document(
                        "mid", ledgerInfo.member().getMid().toString())
                        .append("name", ledgerInfo.member().getName())
                        .append("balance", ledgerInfo.balance()))
                .toList();
        Document ledger = new Document("eid", expense.getEid().toString())
                .append("total", expense.getTotalCost())
                .append("ledger", participants);
        return repo.insertLedger(ledger);
    }
}
