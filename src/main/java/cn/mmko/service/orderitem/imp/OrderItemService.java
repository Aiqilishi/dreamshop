package cn.mmko.service.orderitem.imp;

import cn.mmko.dao.IOrderItemDao;
import cn.mmko.po.OrderItemPo;
import cn.mmko.service.orderitem.IOrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderItemService implements IOrderItemService {
     @Resource
     private IOrderItemDao orderItemDao;
    @Override
    public void createOrderItem(OrderItemPo orderItemPo) {
        orderItemDao.createOrderItem(orderItemPo);
    }
}
