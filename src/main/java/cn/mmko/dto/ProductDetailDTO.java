package cn.mmko.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetailDTO {
    private String productName;    // 商品名称
    private BigDecimal productPrice; // 商品价格
    private Integer productStock;  // 库存数量
    private Integer productSales;  // 销量
    private Integer productStatus; // 商品状态(1:上架,0:下架)
    private String productDesc;    // 商品描述
    private List<ProductImagesDTO> productImages;  // 商品图片(多个图片用逗号分隔)
    private Long viewCount;        // 浏览次数
}
