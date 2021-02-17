package secondMarket.demo.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Image {

    private Long  imageId;

    private String fileOriginalName; // 파일 원래 이름
    private String fileStoreName; // 파일 저장 이름
    private String resourcePath; // 파일 저장 경로

    private Long productId;

    public static Image createImage(String fileOriginalName,String fileStoreName,String resourcePath){
        Image image = new Image();
        image.fileOriginalName = fileOriginalName;
        image.fileStoreName = fileStoreName;
        image.resourcePath = resourcePath;
        return image;
    }


}
