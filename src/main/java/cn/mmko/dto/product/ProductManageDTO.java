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
public class ProductManageDTO {
    private Long productId;
    private String productName;
    private String category;
    private BigDecimal productPrice;
    private Integer productStock;
    private Integer productSales;
    private Integer productStatus;
    private String productDesc;
    private String productImage;
    private Long viewCount;
    private Date createTime;
    private Date updateTime;

}
