package cn.mmko.service.orderitem;

import cn.mmko.po.OrderItemPo;

import java.util.List;

public interface IOrderItemService {
    void createOrderItem(OrderItemPo orderItemPo);

    List<OrderItemPo> queryOrderItemByOrderId(Long orderId);

    List<OrderItemPo> queryOrderItemBySellerId(Long sellerId);
    
    List<OrderItemPo> queryOrderItemBySellerIdWithFilter(Long sellerId, Integer status, Integer itemStatus, String keyword);

    void deliverOrder(Long orderItemId);

    List<Integer> queryOrderItemStatus(Long orderId);
}
