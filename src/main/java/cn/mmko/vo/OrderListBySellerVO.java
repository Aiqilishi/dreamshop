package cn.mmko.vo;

import com.alipay.api.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderListBySellerVO {
    private Long sellerId;
    private String sellerName;
    private BigDecimal totalPrice;
    List<OrderItemVO> orderItemVOList;
}
