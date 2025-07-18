package cn.mmko.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListVO {
    private Long productId;
    private String productName;    // 商品名称
    private String category;       // 分类ID
    private String sellerName;         // 商家ID
    private BigDecimal productPrice; // 商品价格
    private Integer productStock;  // 库存数量
    private Integer productSales;  // 销量
    private Integer productStatus; // 商品状态(1:上架,0:下架)
    private String productImage;   // 商品主图
    private String productDesc;
    private Integer isRecommend;   // 是否推荐(1:是,0:否)
    private Integer isHot;         // 是否热销(1:是,0:否)
    private Long viewCount;        // 浏览次数
}
