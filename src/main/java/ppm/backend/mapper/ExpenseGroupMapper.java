package ppm.backend.mapper;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import ppm.backend.model.ExpenseGroup;
import ppm.backend.repo.SQLColumns;

import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public class ExpenseGroupMapper implements DataMapper<ExpenseGroup>, SQLColumns {
    @Override
    public ExpenseGroup map(SqlRowSet rs) throws SQLException {
        ExpenseGroup eg = new ExpenseGroup();
        UUID gid = UUID.fromString(Objects.requireNonNull(rs.getString(GID)));


        return eg;
    }
}
