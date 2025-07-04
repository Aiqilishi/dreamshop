package cn.mmko.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListDTO {
    private String productId;      // 商品ID
    private Long categoryId;        // 商品分类ID
    private String productName;    // 商品名称
    private BigDecimal productPrice; // 商品价格
    private Integer productSales;  // 销量
    private Integer productStatus; // 商品状态(1:上架,0:下架)
    private String productImage;   // 商品主图
    private Integer isRecommend;   // 是否推荐(1:是,0:否)
    private Integer isHot;         // 是否热销(1:是,0:否)
    private Long viewCount;        // 浏览次数
}
