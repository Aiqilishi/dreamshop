package cn.mmko.dao;

import cn.mmko.po.OrderItemPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IOrderItemDao {
    void createOrderItem(OrderItemPo orderItemPo);

    List<OrderItemPo> queryOrderItemByOrderId(Long orderId);

    List<OrderItemPo> queryOrderItemBySellerId(Long sellerId);

    void deliverOrder(Long orderItemId);

    List<Integer> queryOrderItemStatus(Long orderId);
    
    List<OrderItemPo> queryOrderItemBySellerIdWithFilter(Long sellerId, Integer status, Integer itemStatus, String keyword);
}
