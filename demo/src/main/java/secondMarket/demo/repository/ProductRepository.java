package secondMarket.demo.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import secondMarket.demo.domain.Product;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Product save(Product product){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("product").usingGeneratedKeyColumns("product_id");
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("title",product.getTitle());
        parameters.put("category",product.getCategory());
        parameters.put("price",product.getPrice());
        parameters.put("content",product.getCategory());
        parameters.put("member_id",product.getMemberId());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        product.setProductId(key.longValue());
        return product;
    }
}
