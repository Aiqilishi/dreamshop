package cn.mmko.service.orderitem;

import cn.mmko.po.OrderItemPo;

import java.util.List;

public interface IOrderItemService {
    void createOrderItem(OrderItemPo orderItemPo);

    List<OrderItemPo> queryOrderItemByOrderId(Long orderId);
}
