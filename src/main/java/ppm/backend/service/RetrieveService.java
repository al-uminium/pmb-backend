package ppm.backend.service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import ppm.backend.mapper.CountMapper;
import ppm.backend.mapper.ExpenseMapper;
import ppm.backend.mapper.LedgerMapper;
import ppm.backend.model.Expense;
import ppm.backend.model.Ledger;
import ppm.backend.repo.MongoRepo;
import ppm.backend.repo.SQLColumns;
import ppm.backend.repo.SQLRepo;

import java.util.*;

@Service
public class RetrieveService implements SQLColumns {
    private final LedgerMapper ledgerMapper = new LedgerMapper();
    private final ExpenseMapper expenseMapper = new ExpenseMapper();
    private final CountMapper countMapper = new CountMapper();

    @Autowired
    private HelperService helperSvc;

    @Autowired
    private MongoRepo mongoRepo;

    @Autowired
    private SQLRepo sqlRepo;

    private Optional<List<Ledger>> getLedgerFromMongo(UUID eid) {
        List<Document> queryResult = mongoRepo.getLedgerForExpense(eid);
        if (queryResult.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ledgerMapper.map(queryResult.getFirst()));
    }

    private Optional<Expense> getExpenseDetailsFromDB(UUID eid) {
        SqlRowSet queryResult = sqlRepo.getExpenseDetail(eid);
        if (queryResult.next()) {
            Expense expense = expenseMapper.map(queryResult);
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

    // storing in a hashmap for use later to map the ledger with expense details, uuid stored as string here
    // so that there would be no need to convert it into later when querying in mongo.
    public Optional<HashMap<String,Expense>> getExpenseDetailsForGroupFromDB(String token) {
        SqlRowSet expenseCountRs = sqlRepo.getCountOfExpensesInGroup(token);
        Integer expenseCount = countMapper.map(expenseCountRs);
        System.out.printf("Number of expenses found: %d%n", expenseCount);
        if (expenseCount > 0) {
            HashMap<String, Expense> expenseHashMap = new HashMap<>();
            SqlRowSet queryResult = sqlRepo.getAllExpenseDetailsForGroup(token);
            while (queryResult.next()) {
                Expense expense = expenseMapper.map(queryResult);
                expenseHashMap.put(expense.getEid().toString(), expense);

            }
            return Optional.of(expenseHashMap);
        } else {
            return Optional.empty();
        }

    }

    public Optional<List<Expense>> getAllExpensesForGroup(String token) {
        Optional<HashMap<String, Expense>> expenseHashMapOpt = getExpenseDetailsForGroupFromDB(token);
        if (expenseHashMapOpt.isPresent()) {
            HashMap<String, Expense> expenseHashMap = expenseHashMapOpt.get();
            List<String> eidList = new ArrayList<>(expenseHashMap.keySet());
            List<Document> documentList = mongoRepo.getAllLedgersForExpenseGroup(eidList);
            for (Document ledgerDoc: documentList) {
                Expense expense = expenseHashMap.get(ledgerDoc.getString("eid"));
                List<Ledger> ledger = ledgerMapper.map(ledgerDoc);
                expense.setLedgerParticipants(ledger);
                expenseHashMap.replace(ledgerDoc.getString("eid"),expense);
            }
            return Optional.of(new ArrayList<>(expenseHashMap.values())) ;
        }
        return Optional.empty();
    }

}
