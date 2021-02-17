package secondMarket.demo.model.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductSaveRequest {

    private String category;

    private String title;
    private String content;
    private int price;

    private List<MultipartFile> files;
}
