package ppm.backend.mapper;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.SQLException;

public interface DataMapper<T> {
    T map(SqlRowSet rs) throws SQLException;
}
