package cn.mmko.dao;

import cn.mmko.po.OrderPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IOrderDao {
    void insertOrder(OrderPo build);

    OrderPo queryUnpaidOrder(Long productId, Long userId);

    void updateOrderPaySuccess(Long tradeNo);

    List<String> queryTimeoutOrderList();

    boolean updateOrderClose(String orderId);
}
