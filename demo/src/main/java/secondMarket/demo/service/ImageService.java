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
import java.util.Random;


@Service
public class ImageService {

    @Value("${file.path}")
    private String location;

    public List<Image> saveImages(List<MultipartFile> files) {

        List<Image> images = new ArrayList<>();

        for (MultipartFile file : files) {

            String originalFilename = file.getOriginalFilename();
            System.out.println(originalFilename);
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 10;
            Random random = new Random();
            StringBuilder buffer = new StringBuilder(targetStringLength);
            for (int i = 0; i < targetStringLength; i++) {
                int randomLimitedInt = leftLimit + (int)
                        (random.nextFloat() * (rightLimit - leftLimit + 1));
                buffer.append((char) randomLimitedInt);
            }
            String generatedString = buffer.toString();
            String storeName = generatedString + originalFilename;

            String resourcePath = location + "/" + storeName;
            System.out.println(resourcePath);
            System.out.println(storeName);
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
