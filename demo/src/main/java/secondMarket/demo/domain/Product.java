package secondMarket.demo.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private long productId;
    private String title;
    private String category;
    private int price;
    private String content;
    private long memberId;
}
