package cn.mmko.controller.mq.consumer;

import cn.mmko.controller.handler.OrderTimeoutHandler;
import cn.mmko.message.OrderTimeoutMessage;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
@RocketMQMessageListener(
        topic = "%DLQ%order-consumer-group", // 死信队列
        consumerGroup = "dlq-consumer-group"
)
public class OrderTimeoutDLQConsumer implements RocketMQListener<String> {

    @Resource
    private OrderTimeoutHandler orderTimeoutHandler;

    @Override
    public void onMessage(String messageJson) {
        log.warn("处理死信队列消息: {}", messageJson);

        try {
            OrderTimeoutMessage orderTimeoutMessage = JSON.parseObject(messageJson, OrderTimeoutMessage.class);
            Long orderId = orderTimeoutMessage.getOrderId();

            // 处理死信消息
            orderTimeoutHandler.handleTimeoutOrder(orderId, "死信队列");

        } catch (Exception e) {
            log.error("处理死信队列消息失败: {}", messageJson, e);
            // 可以发送告警通知
        }
    }
}