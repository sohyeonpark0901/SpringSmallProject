package secondMarket.demo.domain;

import lombok.Getter;
import lombok.Setter;
import secondMarket.demo.model.product.ProductSaveRequest;

@Getter
@Setter
public class Product {

    private Long productId;

    private String title;

    private String content;

    private int price;

    private String category;

    private Long memberId;

    public static Product createProduct(ProductSaveRequest productSaveRequest,Long memberId){
        Product product = new Product();
        product.title = productSaveRequest.getTitle();
        product.content = productSaveRequest.getContent();
        product.price = productSaveRequest.getPrice();
        product.category = productSaveRequest.getCategory();
        product.memberId = memberId;
        return product;
    }

}
