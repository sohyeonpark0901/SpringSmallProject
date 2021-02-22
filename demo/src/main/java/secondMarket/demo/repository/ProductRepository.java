package secondMarket.demo.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import secondMarket.demo.domain.DetailPage;
import secondMarket.demo.domain.MainPage;
import secondMarket.demo.domain.Product;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
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

    public int AdminDelete(Long productId){
        int result = jdbcTemplate.update("DELETE FROM product WHERE product_id=?",productId);
        return result;
    }
    public int UserDelete(Long productId,Long memberId){
        int result = jdbcTemplate.update("DELETE FROM product WHERE product_id=? and member_id=?",productId,memberId);
        return result;
    }

    public List<MainPage> findMainPage() {
        List<MainPage> a = jdbcTemplate.query(
                "SELECT product.product_id as product_id, product.title as title, member.name as name ,product.category as category FROM member inner join product where member.member_id = product.member_id ", mainPageRowMapper());

        return a;
    }
    private RowMapper<MainPage> mainPageRowMapper() {
        return (rs, rowNum) -> {
            MainPage mainPage = new MainPage();
            mainPage.setProductId(rs.getLong("product_id"));
            mainPage.setTitle(rs.getString("title"));
            mainPage.setName(rs.getString("name"));
            mainPage.setCategory(rs.getString("category"));
            return mainPage;
        };
    }

    public List<Product> findAll() {
        List<Product> b = jdbcTemplate.query(
                "SELECT * FROM product where product_id = 1", productRowMapper());
        return b;
    }

    //    public List<Product> findDetail(long productId) {
//        List<Product> c = jdbcTemplate.query(
//                "SELECT * FROM product where product_id = ?", productRowMapper(), productId);
//        return c;
//    }
    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> {
            Product product = new Product();
            product.setProductId(rs.getLong("product_id"));
            product.setTitle(rs.getString("title"));
            product.setCategory(rs.getString("category"));
            product.setPrice(rs.getInt("price"));
            product.setContent(rs.getString("content"));
            product.setMemberId(rs.getLong("member_id"));
            return product;
        };
    }

    public List<DetailPage> findDetailPage(long productId) {
        List<DetailPage> d = jdbcTemplate.query(
                "SELECT image.resource_path as resource_path, product.product_id as product_id, product.title as title, product.category as category, member.name as name, product.price as price, product.content as content FROM member inner join product on member.member_id = product.member_id inner join image on product.product_id = image.product_id where product.product_id = ?;", detailPageRowMapper(), productId);
                //"SELECT distinct image.file_store_name as file_store_name, product.product_id as product_id, product.title as title, product.category as category, member.name as name, product.price as price, product.content as content FROM member inner join product on member.member_id = product.member_id inner join image on product.product_id = image.product_id inner join comment on product.product_id = comment.product_id where product.product_id = ?;", commentPageRowMapper(), productId);

        return d;
    }
    private RowMapper<DetailPage> detailPageRowMapper() {
        return (rs, rowNum) -> {
            DetailPage detailPage = new DetailPage();
            detailPage.setFile(rs.getString("resource_path"));
            //detailPage.setFile(rs.getString("file_store_name"));
            detailPage.setProductId(rs.getLong("product_id"));
            detailPage.setTitle(rs.getString("title"));
            detailPage.setName(rs.getString("name"));
            detailPage.setCategory(rs.getString("category"));
            detailPage.setPrice(rs.getString("price"));
            detailPage.setContent(rs.getString("content"));
            return detailPage;
        };
    }
    public List<DetailPage> findCommentPage(long productId) {
        List<DetailPage> co = jdbcTemplate.query(
                "SELECT distinct image.resource_path as resource_path, product.product_id as product_id, product.title as title, product.category as category, member.name as name, product.price as price, product.content as content FROM member inner join product on member.member_id = product.member_id inner join image on product.product_id = image.product_id inner join comment on product.product_id = comment.product_id where product.product_id = ?;", commentPageRowMapper(), productId);
                //"SELECT distinct image.file_store_name as file_store_name, product.product_id as product_id, product.title as title, product.category as category, member.name as name, product.price as price, product.content as content FROM member inner join product on member.member_id = product.member_id inner join image on product.product_id = image.product_id inner join comment on product.product_id = comment.product_id where product.product_id = ?;", commentPageRowMapper(), productId);

        return co;
    }

    public List<DetailPage> findComment2Page(long productId) {
        List<DetailPage> co = jdbcTemplate.query(
                "SELECT product.product_id as product_id, comment.content as comment FROM product inner join comment on product.product_id = comment.product_id where product.product_id = ?;", comment2PageRowMapper(), productId);

        return co;
    }


    private RowMapper<DetailPage> commentPageRowMapper() {
        return (rs, rowNum) -> {
            DetailPage detailPage = new DetailPage();
            detailPage.setFile(rs.getString("resource_path"));
            detailPage.setProductId(rs.getLong("product_id"));
            detailPage.setTitle(rs.getString("title"));
            detailPage.setName(rs.getString("name"));
            detailPage.setCategory(rs.getString("category"));
            detailPage.setPrice(rs.getString("price"));
            detailPage.setContent(rs.getString("content"));

            return detailPage;
        };

    }

    private RowMapper<DetailPage> comment2PageRowMapper() {
        return (rs, rowNum) -> {
            DetailPage detailPage = new DetailPage();
            detailPage.setProductId(rs.getLong("product_id"));
            detailPage.setComment(rs.getString("comment"));
            return detailPage;
        };
    }
}
