package cn.mmko.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Data
@Component
@ConfigurationProperties(prefix = "file.upload")
public class FileUploadConfig {
    // 文件存储基础路径
    private String basePath = "D:/images/";

    // 头像存储路径
    private String avatarPath = "avatars/";

    //商家logo存储路径
    private String logoPath = "logos/";

    // 商品图片存储路径
    private String productPath = "products/";

    // 临时文件路径
    private String tempPath = "temp/";

    // 最大文件大小 (2MB)
    private long maxFileSize = 2 * 1024 * 1024;

    // 允许的文件扩展名
    private List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif", "webp");

    // 缩略图尺寸
    private int thumbnailWidth = 200;
    private int thumbnailHeight = 200;

    // 头像缩略图尺寸
    private int avatarThumbnailWidth = 100;
    private int avatarThumbnailHeight = 100;

    // 图片访问URL前缀
    private String imageUrlPrefix = "http://localhost:8091/images/";
}
