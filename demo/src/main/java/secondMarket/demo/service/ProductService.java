package secondMarket.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import secondMarket.demo.domain.DetailPage;
import secondMarket.demo.domain.Image;
import secondMarket.demo.domain.MainPage;
import secondMarket.demo.domain.Product;
import secondMarket.demo.repository.CommentRepository;
import secondMarket.demo.repository.ImageRepository;
import secondMarket.demo.repository.ProductRepository;

import java.util.List;

@Service

public class ProductService {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private ProductRepository productRepository;
    private ImageRepository imageRepository;
    private CommentRepository commentRepository;


    @Autowired
    public ProductService(ProductRepository productRepository,ImageRepository imageRepository,CommentRepository commentRepository) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.commentRepository = commentRepository;
    }


    public void saveProduct(Product product, List<Image> images) {

        Product saveProduct = productRepository.save(product);

        for (Image image : images) {
            imageRepository.save(image, saveProduct.getProductId());
        }
    }


    public Boolean adminDeleteProduct(Long productId){

        int resultComment = commentRepository.AdminDeleteByProductId(productId);
        int resultImageU = imageRepository.delete(productId);
        int resultProduct = productRepository.AdminDelete(productId);
        logger.info(String.valueOf(resultComment));
        logger.info(String.valueOf(resultProduct));
        logger.info(String.valueOf(resultImageU));
        if((resultComment ==1 && resultProduct ==1) || resultImageU ==1 ){
            return true;
        }
        return false;
    }


    public boolean UserDeleteProduct(Long productId, Long memberId){
        int resultComment = commentRepository.UserDeleteByProductId(productId,memberId);
        int resultImageU = imageRepository.delete(productId);
        int resultProduct = productRepository.UserDelete(productId,memberId);
        logger.info(String.valueOf(resultComment));
        logger.info(String.valueOf(resultProduct));
        logger.info(String.valueOf(resultImageU));
        if( (resultComment ==1 && resultProduct ==1) || resultImageU ==1 ){
            return true;
        }
        return false;
    }

    public List<MainPage> findMainPage() {
        return productRepository.findMainPage();
    }

    public List<MainPage> findMainPageByCategory(String category) {
        return productRepository.findMainPageByCategory(category);
    }

    public List<Product> find() {
        return productRepository.findAll();
    }

//    public List<Product> findDetail(long productId) {
//        return productRepository.findDetail(productId);
//    }

    public List<DetailPage> findDetailPage(long productId) {
        return productRepository.findDetailPage(productId);
    }
    public List<DetailPage> findCommentPage(long productId) {
        return productRepository.findCommentPage(productId);
    }

    public List<DetailPage> findComment2Page(long productId) {
        return productRepository.findComment2Page(productId);
    }
}
