package secondMarket.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import secondMarket.demo.domain.Image;
import secondMarket.demo.exception.ImageException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    @Value("${file.path}")
    private String location;

    public List<Image> saveImages(List<MultipartFile> files) {

        List<Image> images = new ArrayList<>();

        for (MultipartFile file : files) {

            String originalFilename = file.getOriginalFilename();
            String storeName = LocalDateTime.now() + originalFilename;
            String resourcePath = location + storeName;

            fileStore(file, resourcePath);

            Image image = Image.createImage(originalFilename, storeName, resourcePath);
            images.add(image);
        }

        return images;
    }

    private void fileStore(MultipartFile file, String resourcePath) {
        try {
            File store = new File(resourcePath); // 리소스 경로를 이용하여 파일 객체 생성

            if(!store.exists()){
                store.mkdirs(); // 디렉토리가 없을 경우 디렉토리 생성
            }
            file.transferTo(store); // 전송받은 파일은 리소스경로에 저장

        } catch (IOException e) {
            throw new ImageException("이미지 저장 에러");
        }
    }
}
