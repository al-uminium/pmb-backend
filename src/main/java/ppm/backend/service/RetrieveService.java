package ppm.backend.service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import ppm.backend.mapper.ExpenseMapper;
import ppm.backend.mapper.LedgerMapper;
import ppm.backend.mapper.MemberMapper;
import ppm.backend.model.Expense;
import ppm.backend.model.Ledger;
import ppm.backend.model.Member;
import ppm.backend.repo.MongoRepo;
import ppm.backend.repo.SQLColumns;
import ppm.backend.repo.SQLRepo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RetrieveService implements SQLColumns {
    private final LedgerMapper ledgerMapper = new LedgerMapper();
    private final ExpenseMapper expenseMapper = new ExpenseMapper();
    private final MemberMapper memberMapper = new MemberMapper();

    @Autowired
    private MongoRepo mongoRepo;

    @Autowired
    private SQLRepo sqlRepo;

    private Optional<List<Ledger>> getLedgerFromMongo(UUID eid) {
        List<Document> queryResult = mongoRepo.getLedgerForExpense(eid);
        if (queryResult.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ledgerMapper.map(queryResult));
    }

    private Optional<Expense> getExpenseDetailsFromDB(UUID eid) {
        SqlRowSet queryResult = sqlRepo.getExpenseDetails(eid);
        if (queryResult.next()) {
            Expense expense = expenseMapper.map(queryResult);
            Member owner = memberMapper.map(queryResult);
            expense.setOwner(owner);
            return Optional.of(expense);
        }
        return Optional.empty();
    }

    public Optional<Expense> getExpense(UUID eid) {
        Optional<Expense> expenseOpt = getExpenseDetailsFromDB(eid);
        Optional<List<Ledger>> ledgerOpt = getLedgerFromMongo(eid);
        if (expenseOpt.isPresent() && ledgerOpt.isPresent()) {
            Expense expense = expenseOpt.get();
            expense.setLedgerParticipants(ledgerOpt.get());
            return Optional.of(expense);
        }
        return expenseOpt;
    }


}
