package cn.mmko.service.upload.imp;

import cn.mmko.common.Constants;
import cn.mmko.config.FileUploadConfig;
import cn.mmko.enums.ResponseCode;
import cn.mmko.exception.AppException;
import cn.mmko.service.upload.IFileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
@Service
@Slf4j
public class FileUploadService implements IFileUploadService {
    @Resource
    private FileUploadConfig fileUploadConfig;
    @Override
    public String uploadAvatar(MultipartFile file, Long userId) throws Exception {
        validateFile(file);
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String fileName = "avatar_"+ userId +"_"+ UUID.randomUUID() + "." + extension;
        String uploadDir = fileUploadConfig.getBasePath() + fileUploadConfig.getAvatarPath();
        createDirectoryIfNotExists(uploadDir);
        String filePath = uploadDir + fileName;
        File dest =new File(filePath);
        file.transferTo(dest);

        return fileUploadConfig.getImageUrlPrefix()+fileUploadConfig.getAvatarPath()+fileName;
    }

    @Override
    public String uploadSellerLogo(MultipartFile file, Long sellerId) throws Exception {
        validateFile(file);
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String fileName = "shop_logo_"+ sellerId+"_"+ UUID.randomUUID() + "." + extension;
        String uploadDir = fileUploadConfig.getBasePath() + fileUploadConfig.getLogoPath();
        createDirectoryIfNotExists(uploadDir);
        String filePath = uploadDir + fileName;
        File dest =new File(filePath);
        file.transferTo(dest);

        return fileUploadConfig.getImageUrlPrefix()+fileUploadConfig.getLogoPath()+fileName;
    }

    @Override
    public String uploadProductMainImage(MultipartFile file, Long productId) throws Exception {
        validateFile( file);
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String fileName = "product_main_"+ productId+"_"+ UUID.randomUUID() + "." + extension;
        String uploadDir = fileUploadConfig.getBasePath() + fileUploadConfig.getProductPath();
        createDirectoryIfNotExists(uploadDir);
        String filePath = uploadDir + fileName;
        File dest =new File(filePath);
        file.transferTo(dest);
        return fileUploadConfig.getImageUrlPrefix()+fileUploadConfig.getProductPath()+fileName;
    }

    @Override
    public String uploadProductDetailImage(MultipartFile file, Long productId) throws Exception {
        return null;
    }

    @Override
    public void deleteFile(String filePath) {

    }

    @Override
    public void validateFile(MultipartFile file) {
         if(file == null){
             throw new AppException(ResponseCode.IMAGES_NOT_EXIST.getCode(), ResponseCode.IMAGES_NOT_EXIST.getInfo());
         }
         if(file.getSize()>fileUploadConfig.getMaxFileSize()){
             throw new AppException(ResponseCode.IMAGES_TOO_BIG.getCode(), ResponseCode.IMAGES_TOO_BIG.getInfo());
         }
         String fileName = file.getOriginalFilename();
         if(!StringUtils.hasText(fileName)){
             throw new AppException(ResponseCode.IMAGES_NAME_ERROR.getCode(), ResponseCode.IMAGES_NAME_ERROR.getInfo());
         }
         String extension = getFileExtension(fileName).toLowerCase();
         if(!fileUploadConfig.getAllowedExtensions().contains(extension)){
              throw  new AppException(ResponseCode.IMAGES_TYPE_ERROR.getCode(), ResponseCode.IMAGES_TYPE_ERROR.getInfo());
         }
    }
    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            throw new AppException(ResponseCode.IMAGES_NAME_ERROR.getCode(), ResponseCode.IMAGES_NAME_ERROR.getInfo());
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    private void createDirectoryIfNotExists(String dirPath) {
        Path path = Paths.get(dirPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new AppException(ResponseCode.UN_ERROR.getCode(), ResponseCode.UN_ERROR.getInfo());
            }
        }
    }

    private void generateThumbnail(String sourcePath, String targetPath) {
        // 这里可以使用ImageIO或其他图片处理库生成缩略图
        // 为了简化，这里只是复制原文件
        try {
            Files.copy(Paths.get(sourcePath), Paths.get(targetPath));
        } catch (IOException e) {
            log.error("生成缩略图失败", e);
        }
    }
}
