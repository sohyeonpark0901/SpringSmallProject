package secondMarket.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import secondMarket.demo.domain.Member;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);
    }



    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("member_id");
        String role = "user";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
        parameters.put("password",member.getPassword());
        parameters.put("email",member.getEmail());
        parameters.put("address",member.getAddress());
        parameters.put("phone",member.getPhone());
        parameters.put("role",role);
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setMemberId(key.longValue());
        return member;
    }


    public Optional<Member> findById(Long memberId) {
        List<Member> result = jdbcTemplate.query("select * from member where member_id = ?",memberRowMapper(),memberId);
        return result.stream().findAny();
    }



    public Optional<Member> findByEmail(String email) {
        List<Member> result = jdbcTemplate.query("select * from member where email = ?",memberRowMapper(),email);
        return result.stream().findAny();
    }


    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member where role=?",memberRowMapper(),"user");
    }

    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setMemberId(rs.getLong("member_id"));
            member.setPassword(rs.getString("password"));
            member.setAddress(rs.getString("address"));
            member.setName(rs.getString("name"));
            member.setEmail(rs.getString("email"));
            member.setPhone(rs.getString("phone"));
            member.setRole(rs.getString("role"));
            return member;
        };
    }

}
