package secondMarket.demo.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import secondMarket.demo.domain.Image;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ImageRepository {
    private final JdbcTemplate jdbcTemplate;

    public ImageRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Image save(Image image,Long productId){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("image").usingGeneratedKeyColumns("image_id");
        Map<String,Object> parameters = new HashMap<>();
        // TODO 파라미터 명 바꾸기
        parameters.put("file_original_name",image.getFileOriginalName());
        parameters.put("file_store_name",image.getFileStoreName());
        parameters.put("resource_path",image.getResourcePath());
        parameters.put("product_id",productId);
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        image.setImageId(key.longValue());
        return image;

    }

    public int adminDelete(Long productId){
        int result = jdbcTemplate.update("DELETE FROM image WHERE product_id=?",productId);
        return result;
    }
    public int userDelete(Long productId,Long memberId){
        int result = jdbcTemplate.update("DELETE FROM image WHERE image.product_id IN (select product.product_id from product where product.product_id=? and product.member_id=?);",productId,memberId);
        return result;
    }



}
