package cn.mmko.dto.product;

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
public class ProductUpdateDTO {
    private Long productId;        // 商品ID
    private String productName;    // 商品名称
    private BigDecimal productPrice; // 商品价格
    private Integer productStock;  // 库存数量
    private Integer productStatus; // 商品状态(1:上架,0:下架)
    private String productDesc;    // 商品描述
    private String productImage;   // 商品主图
    private Integer isRecommend;   // 是否推荐(1:是,0:否)
}
