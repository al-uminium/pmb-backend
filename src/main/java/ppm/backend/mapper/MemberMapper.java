package ppm.backend.mapper;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import ppm.backend.model.Member;
import ppm.backend.repo.SQLColumns;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class MemberMapper implements DataMapper<Member>, SQLColumns {
    @Override
    public Member map(SqlRowSet rs) {
        Member member = new Member();

        UUID mid = UUID.fromString(Objects.requireNonNull(rs.getString(MID)));
        String name = rs.getString(NAME);
        Optional<String> uidStr = Optional.ofNullable(rs.getString(UID));
        uidStr.ifPresent(s -> member.setUid(UUID.fromString(s)));
        member.setMid(mid);
        member.setName(name);

        return member;
    }
}
