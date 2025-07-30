package cn.mmko.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartListVO {
    private Long productId;
    private Integer quantity;
    private Long sellerId;
    private String productName;
    private String productImage;
    private BigDecimal productPrice;
}
