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
public class ProductCreateDTO {
    private String productName;    // 商品名称
    private Long categoryId;       // 分类ID
    private Long userId;         // 商家ID
    private BigDecimal productPrice; // 商品价格
    private Integer productStock;  // 库存数量
    private String productDesc;    // 商品描述
    private String productImage;   // 商品主图
}
