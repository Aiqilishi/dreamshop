package cn.mmko.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImagesVO {
    private Long imageId; // 图片ID
    private String imageUrl;   // 图片URL
    private Integer sortOrder; // 排序
}
