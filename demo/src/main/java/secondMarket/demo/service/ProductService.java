package secondMarket.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import secondMarket.demo.domain.Image;
import secondMarket.demo.domain.Product;
import secondMarket.demo.repository.ImageRepository;
import secondMarket.demo.repository.ProductRepository;

import java.util.List;

@Service

public class ProductService {


    private ProductRepository productRepository;
    private ImageRepository imageRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void saveProduct(Product product, List<Image> images) {

        Product saveProduct = productRepository.save(product);

        for (Image image : images) {
            imageRepository.save(image, saveProduct.getProductId());
        }
    }

}
