package cn.mmko.dao;

import cn.mmko.po.OrderItemPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IOrderItemDao {
    void createOrderItem(OrderItemPo orderItemPo);
}
