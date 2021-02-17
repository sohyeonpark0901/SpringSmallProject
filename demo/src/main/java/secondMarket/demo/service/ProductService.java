package secondMarket.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import secondMarket.demo.domain.Image;
import secondMarket.demo.domain.Product;
import secondMarket.demo.repository.ImageRepository;
import secondMarket.demo.repository.ProductRepository;

@Service

public class ProductService {

    private ProductRepository productRepository;
    private ImageRepository imageRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public long create(Product product,Image image){
        productRepository.save(product);
        imageRepository.save(image,product.getProductId());
        return
    }

}
