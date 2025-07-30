package cn.mmko.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * 订单明细表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class OrderItemPo {
    private Long itemId;         // 明细ID
    private Long orderId;        // 订单ID
    private Long productId;      // 商品ID
    private Long sellerId;            // 卖家ID
    private String productName;  // 商品名称
    private Integer itemStatus;  // 明细状态
    private java.math.BigDecimal productPrice; // 商品单价
    private Integer quantity;    // 购买数量
} 