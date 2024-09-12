package ppm.backend.mapper;

import com.mongodb.lang.Nullable;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import ppm.backend.model.Currency;
import ppm.backend.model.Expense;
import ppm.backend.repo.SQLColumns;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class ExpenseMapper implements DataMapper<Expense>, SQLColumns {
    @Override
    public Expense map(SqlRowSet rs) {

        UUID eid = UUID.fromString(Objects.requireNonNull(rs.getString(EID)));
        String title = Objects.requireNonNull(rs.getString(TITLE));
        Currency currency = Currency.valueOf(rs.getString(CURRENCY));
        Double totalCost = rs.getDouble(TOTAL_COST);
        LocalDateTime createdAt = (LocalDateTime) rs.getObject(CREATED_AT);
        LocalDateTime updatedAt = (LocalDateTime) rs.getObject(UPDATED_AT);
        return new Expense(eid, title, currency, totalCost, createdAt, updatedAt);
    }
}
