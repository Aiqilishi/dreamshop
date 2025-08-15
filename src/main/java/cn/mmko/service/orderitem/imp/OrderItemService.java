package cn.mmko.service.orderitem.imp;

import cn.mmko.dao.IOrderItemDao;
import cn.mmko.po.OrderItemPo;
import cn.mmko.service.orderitem.IOrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderItemService implements IOrderItemService {
     @Resource
     private IOrderItemDao orderItemDao;
    @Override
    public void createOrderItem(OrderItemPo orderItemPo) {
        orderItemDao.createOrderItem(orderItemPo);
    }

    @Override
    public List<OrderItemPo> queryOrderItemByOrderId(Long orderId) {
        return orderItemDao.queryOrderItemByOrderId(orderId);
    }

    @Override
    public List<OrderItemPo> queryOrderItemBySellerId(Long sellerId) {
        return  orderItemDao.queryOrderItemBySellerId(sellerId);
    }

    @Override
    public void deliverOrder(Long orderItemId) {
        orderItemDao.deliverOrder(orderItemId);
    }

    @Override
    public List<Integer> queryOrderItemStatus(Long orderId) {
        return orderItemDao.queryOrderItemStatus(orderId);
    }
    
    @Override
    public List<OrderItemPo> queryOrderItemBySellerIdWithFilter(Long sellerId, Integer status, Integer itemStatus, String keyword) {
        return orderItemDao.queryOrderItemBySellerIdWithFilter(sellerId, status, itemStatus, keyword);
    }
}
