package cn.mmko.service.order;

import cn.mmko.dto.OrderCreateDTO;
import com.alipay.api.AlipayApiException;

import java.util.List;

public interface IOrderService {
    String createOrder(OrderCreateDTO orderCreateDTO, Long userId) throws AlipayApiException;

    void changeOrderPaySuccess(Long tradeNo);

    void releaseProductStock(Long aLong);

    List<String> queryTimeoutOrderList();

    boolean changeOrderClose(String orderId);
}
