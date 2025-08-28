package cn.mmko.controller.mq.consumer;

import cn.mmko.common.RocketMQConstants;
import cn.mmko.controller.handler.OrderTimeoutHandler;
import cn.mmko.message.OrderTimeoutMessage;
import cn.mmko.service.order.IOrderService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
@RocketMQMessageListener(topic = RocketMQConstants.ORDER_TIMEOUT_TOPIC,
 consumerGroup = RocketMQConstants.ORDER_CONSUMER_GROUP,
 selectorExpression = RocketMQConstants.ORDER_TIMEOUT_CLOSE_TAG)
public class OrderTimeoutConsumer implements RocketMQListener<String> {
    @Resource
    private OrderTimeoutHandler orderTimeoutHandler;
    @Override
    public void onMessage(String  messageJson) {
        try {
            OrderTimeoutMessage orderTimeoutMessage = JSON.parseObject(messageJson, OrderTimeoutMessage.class);
            Long orderId = orderTimeoutMessage.getOrderId();

            // 调用统一处理方法
            orderTimeoutHandler.handleTimeoutOrder(orderId, "MQ消息");

        } catch (Exception e) {
            log.error("MQ消费超时订单失败，消息: {}", messageJson, e);
            throw e;
        }

    }
}
