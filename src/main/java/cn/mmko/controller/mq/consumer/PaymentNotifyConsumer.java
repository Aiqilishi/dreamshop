package cn.mmko.controller.mq.consumer;

import cn.mmko.common.RocketMQConstants;
import cn.mmko.controller.handler.StockDomainService;
import cn.mmko.po.OrderPo;
import cn.mmko.service.order.IOrderService;
import cn.mmko.service.orderitem.IOrderItemService;
import cn.mmko.service.product.IProductService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
@RocketMQMessageListener(
        topic = "PAYMENT_TOPIC",
        consumerGroup = "payment-notify-consumer"
)
public class PaymentNotifyConsumer implements RocketMQListener<String> {

    @Resource
    private IOrderService orderService;
    @Resource
    private StockDomainService stockDomainService;


    @Override
    public void onMessage(String paymentData) {
        log.info("接收到支付结果异步通知: {}", paymentData);

        try {
            JSONObject data = JSON.parseObject(paymentData);
            Long orderId = data.getLong("orderId");
            String tradeStatus = data.getString("tradeStatus");
            String tradeNo = data.getString("tradeNo");

            log.info("处理支付通知 - 订单ID: {}, 交易状态: {}, 支付宝交易号: {}",
                    orderId, tradeStatus, tradeNo);

            // 幂等性检查
            OrderPo order = orderService.queryOrderById(orderId);
            if (order.getOrderStatus() != 0) { // 不是待支付状态
                log.info("订单状态已变更，跳过处理，订单ID: {}, 当前状态: {}",
                        orderId, order.getOrderStatus());
                return;
            }

            if ("TRADE_SUCCESS".equals(tradeStatus)) {
                // 更新订单支付状态
                orderService.changeOrderPaySuccess(orderId);
                // 确认库存（冻结 → 已售）
                stockDomainService.releaseStockByOrderId(orderId);
                log.info("订单支付成功处理完成，订单ID: {}", orderId);
            }

        } catch (Exception e) {
            log.error("处理支付结果异步通知失败: {}", paymentData, e);
            throw e; // 让RocketMQ重试
        }
    }
}