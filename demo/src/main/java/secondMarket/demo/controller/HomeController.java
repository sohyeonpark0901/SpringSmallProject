package secondMarket.demo.controller;

import com.sun.tools.javac.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import secondMarket.demo.domain.MainPage;
import secondMarket.demo.domain.Product;
import secondMarket.demo.service.ProductService;

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
    public String list(Model model){
        List<MainPage> mainPageList = productService.findMainPage();
        model.addAttribute("mainPageList", mainPageList);
        return "home";
    }

}