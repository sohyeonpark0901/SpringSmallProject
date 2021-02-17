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
        parameters.put("file_originalName",image.getFileOriginalName());
        parameters.put("fileStoreName",image.getFileStoreName());
        parameters.put("resourcePath",image.getResourcePath());
        parameters.put("product_id",image.getProductId());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        image.setImageId(key.longValue());
        return image;

    }
}
