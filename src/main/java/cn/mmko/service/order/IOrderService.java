package cn.mmko.service.order;

import cn.mmko.dto.OrderCreateDTO;
import com.alipay.api.AlipayApiException;

public interface IOrderService {
    String createOrder(OrderCreateDTO orderCreateDTO, Long userId) throws AlipayApiException;

    void changeOrderPaySuccess(Long tradeNo);
}
