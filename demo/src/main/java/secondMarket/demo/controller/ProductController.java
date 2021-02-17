package secondMarket.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import secondMarket.demo.model.product.ProductSaveRequest;

@Controller
public class ProductController {
    /**
     * 1. 멤버를 찾는다
     * 2. 카테고리를 찾는다
     * 3. 상품을 생성하고 등록한다. -> 맴버의 id와 카테고리의 id를 외래키로 넣는다. -> 상품의 아이디를 찾는다.
     * 4. 파일을 로컬에 저장
     * 5. 파일을 저장한 image 앤티티를 생성하고 거기에 외래키로 상품의 id 넣는다
     *
     */
//    @PostMapping("/products")
//    public String saveProduct(@ModelAttribute ProductSaveRequest productSaveRequest){
//
//    }
}
