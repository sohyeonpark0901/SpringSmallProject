package secondMarket.demo.model.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductSaveRequest {
    private Long memberId;
    private Long categoryId;
    private String title;
    private String content;
    private int price;
    private MultipartFile multipartFile;
}
