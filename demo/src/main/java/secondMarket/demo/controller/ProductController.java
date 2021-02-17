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
import secondMarket.demo.service.ImageService;
import secondMarket.demo.service.ProductService;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final ImageService imageService;

    @Autowired
    public ProductController(ProductService productService,ImageService imageService){
        this.productService = productService;
        this.imageService = imageService;
    }

    @GetMapping("/products/new")
    public String createForm(){
        return "products/createProductForm";
    }

    /**
     * 1. 멤버를 찾는다
     * 2. 카테고리를 찾는다
     * 3. 상품을 생성하고 등록한다. -> 맴버의 id와 카테고리의 id를 외래키로 넣는다. -> 상품의 아이디를 찾는다.
     * 4. 파일을 로컬에 저장
     * 5. 파일을 저장한 image 앤티티를 생성하고 거기에 외래키로 상품의 id 넣는다
     *
     */
    @PostMapping("/products/new")
    public String saveProduct(@ModelAttribute ProductSaveRequest productSaveRequest, HttpSession session) {

        Member loginMember = (Member) session.getAttribute("memberEmail");

        Long memberId = loginMember.getMemberId();

        Product product = Product.createProduct(productSaveRequest, memberId);

        List<Image> images = imageService.saveImages(productSaveRequest.getFiles());

        productService.saveProduct(product,images);

        return "redirect:/";
    }

}
