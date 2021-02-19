package secondMarket.demo.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import secondMarket.demo.domain.Comment;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CommentRepository {
    private final JdbcTemplate jdbcTemplate;

    public CommentRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Comment save(Comment comment){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("comment").usingGeneratedKeyColumns("comment_id");
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("content",comment.getContent());
        parameters.put("member_id",comment.getMemberId());
        parameters.put("product_id",comment.getProductId());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        comment.setCommentId(key.longValue());
        return comment;
    }
    public int AdminDelete(Long commentId,Long productId){
       int result = jdbcTemplate.update("delete from comment where comment_id=? AND product_id=?",commentId,productId);
       return result;
    }

    public int UserDelete(Long memberId ,Long commentId,Long productId){
        int result = jdbcTemplate.update("DELETE FROM comment where comment_id=? AND member_id=? AND product_id=?",commentId,memberId,productId);
        return result;
    }
    public int UserDeleteByProductId(Long productId,Long memberId){
        int result = jdbcTemplate.update("DELETE FROM comment WHERE product_id=? and member_id=?",productId,memberId);
        return result;
    }
    public int AdminDeleteByProductId(Long productId){
        int result = jdbcTemplate.update("DELETE FROM comment WHERE product_id=? ",productId);
        return result;
    }
}
