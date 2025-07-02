package cn.mmko.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class ProductPo {
    private Long productId;        // 商品ID
    private String productName;    // 商品名称
    private Long categoryId;       // 分类ID
    private BigDecimal productPrice; // 商品价格
    private Integer productStock;  // 库存数量
    private Integer productSales;  // 销量
    private Integer productStatus; // 商品状态(1:上架,0:下架)
    private String productDesc;    // 商品描述
    private String productImage;   // 商品主图
    private String productImages;  // 商品图片(多个图片用逗号分隔)
    private Integer isRecommend;   // 是否推荐(1:是,0:否)
    private Integer isHot;         // 是否热销(1:是,0:否)
    private Long viewCount;        // 浏览次数
    private Date createTime;       // 创建时间
    private Date updateTime;       // 更新时间
} 