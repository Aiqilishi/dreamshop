package cn.mmko.service.upload;

import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadService {
    /**
     * 上传头像
     */
    String uploadAvatar(MultipartFile file, Long userId) throws Exception;
    /**
     * 上传商家logo
     */
    String uploadSellerLogo(MultipartFile file, Long sellerId) throws Exception;

    /**
     * 上传商品主图
     */
    String uploadProductMainImage(MultipartFile file, Long productId) throws Exception;

    /**
     * 上传商品详情图
     */
    String uploadProductDetailImage(MultipartFile file, Long productId) throws Exception;

    /**
     * 删除文件
     */
    void deleteFile(String filePath);

    /**
     * 验证文件
     */
    void validateFile(MultipartFile file);
}
