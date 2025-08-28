package cn.mmko.controller.mq.consumer;

import cn.mmko.common.RocketMQConstants;
import cn.mmko.controller.mq.producer.imp.OrderMessageProducer;
import cn.mmko.message.OrderMessage;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = RocketMQConstants.ORDER_TOPIC,
        consumerGroup = RocketMQConstants.ORDER_CONSUMER_GROUP,
          selectorExpression = RocketMQConstants.ORDER_CREATE_TAG)
public class OrderCreateConsumer implements RocketMQListener<OrderMessage> {

    @Override
    public void onMessage(OrderMessage orderMessage) {

    }
}
