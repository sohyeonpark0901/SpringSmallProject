package secondMarket.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import secondMarket.demo.domain.Image;
import secondMarket.demo.domain.Member;
import secondMarket.demo.domain.Product;
import secondMarket.demo.model.product.ProductSaveRequest;
import secondMarket.demo.service.ProductService;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    /**
     * 1. 멤버를 찾는다
     * 2. 카테고리를 찾는다
     * 3. 상품을 생성하고 등록한다. -> 맴버의 id와 카테고리의 id를 외래키로 넣는다. -> 상품의 아이디를 찾는다.
     * 4. 파일을 로컬에 저장
     * 5. 파일을 저장한 image 앤티티를 생성하고 거기에 외래키로 상품의 id 넣는다
     *
     */
    @GetMapping("/products/new")
    public String createForm(){
        return "products/createProductForm";
    }

    @Value("${file.path}")
    private String fileLocation;

    @PostMapping("/products/new")
    public String saveProduct(@ModelAttribute ProductSaveRequest productSaveRequest, HttpSession session, @RequestParam MultipartFile file) {
        Member loginMember = (Member) session.getAttribute("memberEmail");
        Long memberId = loginMember.getMemberId();
        Product product = new Product();
        Image image = new Image();

        String storeName = LocalDateTime.now() + file.getOriginalFilename();
        String resourcePath = fileLocation + storeName;

        try {
            File store = new File(resourcePath);
            if (!store.exists()) {
                store.mkdir();
            }
            file.transferTo(store);
            image.setSrc(storeName);
            product.setTitle(productSaveRequest.getTitle());
            product.setContent(productSaveRequest.getContent());
            product.setCategory(productSaveRequest.getCategory());
            product.setPrice(productSaveRequest.getPrice());
            product.setMemberId(memberId);
            productService.create(product); //3.까지 구현

        } catch(IOException e){
            throw new IllegalStateException(e);
        }
        return "rdirect:/";
    }

}
