package cn.mmko.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class OrderPo {
    private Long orderId;        // 订单ID
    private Long userId;         // 下单用户ID
    private Integer orderStatus; // 订单状态(0:待支付,1:已支付,2:已发货,3:已完成,4:已取消)
    private java.math.BigDecimal totalAmount; // 订单总金额
    private java.util.Date createTime;     // 下单时间
    private java.util.Date payTime;        // 支付时间
    private java.util.Date deliveryTime;   // 发货时间
    private java.util.Date finishTime;     // 完成时间
} 