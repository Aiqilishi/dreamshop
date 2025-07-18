package cn.mmko.service.order.imp;
import cn.hutool.core.util.IdUtil;
import cn.mmko.dao.IOrderDao;
import cn.mmko.dto.OrderCreateDTO;
import cn.mmko.po.OrderItemPo;
import cn.mmko.po.OrderPo;
import cn.mmko.service.order.IOrderService;
import cn.mmko.service.orderitem.IOrderItemService;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
@Slf4j
public class OrderService implements IOrderService {
    @Value("${alipay.notify_url}")
    private String notifyUrl;
    @Resource
    private AlipayClient alipayClient;
    @Resource
    private IOrderDao orderDao;
    @Resource
    private IOrderItemService orderItemService;
    @Override
    public String createOrder(OrderCreateDTO orderCreateDTO, Long userId) throws AlipayApiException {
        OrderPo orderPo = orderDao.queryUnpaidOrder(orderCreateDTO.getProductId(),userId);
        if(orderPo!=null&&orderPo.getOrderStatus()==0){
            log.info("用户已经存在未完成订单");
            return doPrepayOrder(orderCreateDTO.getProductName(),
                    orderPo.getOrderId(),
                    orderPo.getTotalAmount());
        }
        Long orderId = IdUtil.getSnowflake(1, 1).nextId();
        orderDao.insertOrder(OrderPo.builder()
                .orderId(orderId)
                .userId(userId)
                .sellerId(orderCreateDTO.getSellerId())
                .productId(orderCreateDTO.getProductId())
                .orderStatus(0)
                .totalAmount(orderCreateDTO.getTotalAmount())
                .build());
        orderItemService.createOrderItem(OrderItemPo.builder()
                .orderId(orderId)
                .productId(orderCreateDTO.getProductId())
                .productName(orderCreateDTO.getProductName())
                .productPrice(orderCreateDTO.getPrice())
                .quantity(orderCreateDTO.getQuantity())
                .build());
        return doPrepayOrder(orderCreateDTO.getProductName(),
                orderId,
                orderCreateDTO.getTotalAmount());

    }

    @Override
    public void changeOrderPaySuccess(Long orderId) {
        orderDao.updateOrderPaySuccess(orderId);
    }

    private String doPrepayOrder( String productName, Long orderId, BigDecimal totalAmount) throws AlipayApiException {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(notifyUrl);

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderId);
        bizContent.put("total_amount", totalAmount.toString());
        bizContent.put("subject", productName);
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(bizContent.toString());
        return alipayClient.pageExecute(request).getBody();

    }
}
