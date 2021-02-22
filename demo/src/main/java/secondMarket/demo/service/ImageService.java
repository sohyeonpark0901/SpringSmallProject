package secondMarket.demo.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import secondMarket.demo.domain.Image;
import secondMarket.demo.exception.ImageException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public List<Image> saveImages(List<MultipartFile> files)  {

        List<Image> images = new ArrayList<>();

        for (MultipartFile file : files) {


            String storeName = UUID.randomUUID().toString() + file.getOriginalFilename();

            try {
                amazonS3Client.putObject(new PutObjectRequest(bucket,storeName,file.getInputStream(),null)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new IllegalStateException();
            }


            String resourcePath = amazonS3Client.getUrl(bucket,storeName).toString();

            Image image = Image.createImage(file.getOriginalFilename(), storeName, resourcePath);
            images.add(image);

        }


        return images;
    }


    /**
     * 변환 파일 삭제
     */
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    /**
     * MultiPartFile -> File로 변환
     */
    private Optional<File> convert(MultipartFile file) throws IOException {

        File convertFile = new File(file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}