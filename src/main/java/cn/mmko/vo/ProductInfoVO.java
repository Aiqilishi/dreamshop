package cn.mmko.vo;

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
public class ProductInfoVO {
    private Long productId;
    private String productName;    // 商品名称
    private String categoryName;       // 商品类目
    private String sellerName;         // 商家名
    private Long sellerId;
    private String address;
    private String LogoUrl;
    private BigDecimal productPrice; // 商品价格
    private Integer productStock;  // 库存数量
    private Integer productSales;  // 销量
    private Integer productStatus; // 商品状态(1:上架,0:下架)
    private String productDesc;    // 商品描述
    private Integer isRecommend;   // 是否推荐(1:是,0:否)
    private Integer isHot;         // 是否热销(1:是,0:否)
    private Long viewCount;        // 浏览次数
    private List<ProductImagesVO> productImages;// 商品图片
}
