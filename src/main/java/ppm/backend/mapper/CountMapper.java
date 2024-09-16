package ppm.backend.mapper;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import ppm.backend.interfaces.DataMapper;
import ppm.backend.repo.SQLColumns;

public class CountMapper implements DataMapper<Integer>, SQLColumns {

    @Override
    public Integer map(SqlRowSet rs) {
        rs.next();
        return rs.getInt(COUNT);
    }
}
