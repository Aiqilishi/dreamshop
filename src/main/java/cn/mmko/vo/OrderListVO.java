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
public class OrderListVO {
    private Long orderId;
    private BigDecimal totalPrice;
    private Date createTime;
    private Date payTime;
    private Date deliveryTime;
    private Date finishTime;
    List<OrderListBySellerVO> orderListBySellerVOList;
}
