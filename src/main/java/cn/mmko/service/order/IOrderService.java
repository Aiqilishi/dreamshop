package cn.mmko.service.order;

import cn.mmko.dto.OrderCreateDTO;
import cn.mmko.vo.OrderBackgroundListVO;
import cn.mmko.vo.OrderListVO;
import cn.mmko.vo.OrderNumberVO;
import com.alipay.api.AlipayApiException;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IOrderService {
    String createOrder(OrderCreateDTO orderCreateDTO, Long userId, Long id) throws AlipayApiException;

    void changeOrderPaySuccess(Long tradeNo);

    void releaseProductStock(Long aLong);

    List<Long> queryTimeoutOrderList();

    boolean changeOrderClose(Long orderId);

    OrderNumberVO queryOrderNumber(Long userId);

    PageInfo<OrderListVO> queryOrderList(Integer pageNum, Integer pageSize, Long userId, Integer status);

    PageInfo<OrderBackgroundListVO> queryOrderBackgroundList(Integer pageNum, Integer pageSize, Long userId, Integer status, Integer itemStatus, String keyword);

    void deliverOrder(Long orderItemId, Long orderId);

    void backProductStock(Long aLong);
}
