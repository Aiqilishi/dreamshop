package cn.mmko.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 商品图片表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class ProductImagePo {
    private Long imageId;      // 图片ID
    private Long productId;    // 商品ID
    private String imageUrl;   // 图片URL
    private Integer sortOrder; // 排序
    private Date createTime;   // 上传时间
} 