package secondMarket.demo.controller;

import com.sun.tools.javac.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import secondMarket.demo.domain.MainPage;
import secondMarket.demo.domain.Member;
import secondMarket.demo.domain.Product;
import secondMarket.demo.service.ProductService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {
    private final ProductService productService;

    @Autowired
    public HomeController(ProductService productService) {

        this.productService = productService;

    }
    //    @GetMapping("/")
//    public String home() {
//        return "home";
//    }
    @GetMapping("/")
    public String list(Model model, HttpSession session){
        Member loginMember = (Member) session.getAttribute("memberEmail");

        List<MainPage> mainPageList = productService.findMainPage();
        model.addAttribute("mainPageList", mainPageList);
        if(loginMember == null){
            return "home";
        }
        return "home2";
    }

    @GetMapping("/{category}")
    public String listbyCategory(@PathVariable("category") String category, Model model, HttpSession session){
        Member loginMember = (Member) session.getAttribute("memberEmail");

        List<MainPage> mainPageList = productService.findMainPageByCategory(category);
        model.addAttribute("mainPageList", mainPageList);
        if(loginMember == null){
            return "home";
        }
        return "home2";
    }

}