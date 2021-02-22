package secondMarket.demo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import secondMarket.demo.domain.DetailPage;
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

    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final ProductService productService;
    private final ImageService imageService;

    @Autowired
    public ProductController(ProductService productService,ImageService imageService){
        this.productService = productService;
        this.imageService = imageService;
    }

    @GetMapping("/products/new")
    public String createForm(HttpSession session){
        Member loginMember = (Member) session.getAttribute("memberEmail");

        if(loginMember == null){
            throw new IllegalStateException("로그인을 해주세요.");
        }
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

        if(loginMember == null){
            throw new IllegalStateException("로그인을 해주세요");
        }

        Product product = Product.createProduct(productSaveRequest, memberId);

        List<Image> images = imageService.saveImages(productSaveRequest.getFiles());

        productService.saveProduct(product,images);

        return "redirect:/";
    }

    @DeleteMapping("/products/{productId}")
    public String deleteProduct(@PathVariable Long productId,HttpSession session){
        Member loginMember = (Member) session.getAttribute("memberEmail");

        Long memberId = loginMember.getMemberId();
        String memberRole = loginMember.getRole();

        if(loginMember == null){

            throw new IllegalStateException("로그인을 해주세요");
        }

        if(memberRole.equals("admin")){
            boolean result = productService.adminDeleteProduct(productId);
            if(result == true){
                return "redirect:/";
            }
            else {

                throw new IllegalStateException("게시물이 삭제되지 않습니다.");

            }

        }
        boolean result = productService.UserDeleteProduct(productId,memberId);
        if(result == true){
           return "redirect:/";
        }
        else {
            logger.info(String.valueOf(result));
            throw new IllegalStateException("게시이 삭제되지 않습니다.");

        }

    }


    @GetMapping("/products")
    public String list(Model model) {
        List<Product> boardList = productService.find();
        model.addAttribute("boardList", boardList);
        return "/products/productList";
    }


    @GetMapping("/products/{productId}")
    public String list(@PathVariable("productId") Long productId, Model model) {
        List<DetailPage> detailList = productService.findDetailPage(productId);
        model.addAttribute("detailList", detailList);
        List<DetailPage> comment2List = productService.findComment2Page(productId);
        model.addAttribute("comment2List", comment2List);
        return "/products/product";
    }


    @GetMapping("/products/{productId}/{productId}")
    public String list2(@PathVariable("productId") long productId, Model model) {
        List<DetailPage> commentList = productService.findCommentPage(productId);
        model.addAttribute("commentList", commentList);
        List<DetailPage> comment2List = productService.findComment2Page(productId);
        model.addAttribute("comment2List", comment2List);
        return "/products/post2";
    }
}
