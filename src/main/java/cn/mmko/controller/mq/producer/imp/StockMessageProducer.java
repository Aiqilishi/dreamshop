package cn.mmko.controller.mq.producer.imp;

import cn.mmko.common.RocketMQConstants;
import cn.mmko.controller.mq.producer.IStockMessageProducer;
import cn.mmko.message.OrderMessage;
import cn.mmko.message.OrderTimeoutMessage;
import cn.mmko.message.StockMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Component
@Slf4j
public class StockMessageProducer implements IStockMessageProducer {
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Override
    public void sendMessage(String topic, Object message) {
        rocketMQTemplate.convertAndSend(topic, message);
    }

    @Override
    public void sendMessage(String topic, String tag, Object message) {
        rocketMQTemplate.convertAndSend(topic + ":" + tag, message);
    }

    @Override
    public void sendDelayMessage(String topic, Object message, int delayLevel) {
        Message< Object> msg= MessageBuilder
                .withPayload(message)
                .setHeader(RocketMQHeaders.KEYS,generateMessageKeys(message))
                .build();
        rocketMQTemplate.syncSend(topic, msg, 3000, delayLevel);
    }



    @Override
    public void sendTransactionMessage(String topic, Object message, Object localTransactionArg) {
        Message<Object> msg= MessageBuilder
                .withPayload(message)
                .setHeader(RocketMQHeaders.KEYS,generateMessageKeys(message))
                .setHeader(RocketMQHeaders.TAGS, "TRANSACTION")
                .setHeader("localTransactionArg", localTransactionArg)
                .build();
        TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction(topic, msg, localTransactionArg);
        log.info("发送事务消息结果：{},消息id： {}", result.getLocalTransactionState(),result.getTransactionId());
    }
    @Override
    public void sendStockLockMessage(Long orderId) {
        StockMessage stockMessage = StockMessage.builder()
                .orderId(orderId)
                .action("LOCK")
                .timestamp(System.currentTimeMillis())
                .build();

        sendMessage(RocketMQConstants.STOCK_TOPIC,
                RocketMQConstants.STOCK_LOCK_TAG,
                stockMessage);

        log.info("发送库存锁定消息成功，orderId: {}", orderId);
    }

    @Override
    public void sendStockReleaseMessage(Long orderId) {
        StockMessage stockMessage = StockMessage.builder()
                .orderId(orderId)
                .action("RELEASE")
                .timestamp(System.currentTimeMillis())
                .build();

        sendMessage(RocketMQConstants.STOCK_TOPIC,
                RocketMQConstants.STOCK_RELEASE_TAG,
                stockMessage);

        log.info("发送库存释放消息成功，orderId: {}", orderId);
    }

    @Override
    public void sendStockBackMessage(Long orderId) {
        StockMessage stockMessage = StockMessage.builder()
                .orderId(orderId)
                .action("BACK")
                .timestamp(System.currentTimeMillis())
                .build();

        sendMessage(RocketMQConstants.STOCK_TOPIC,
                RocketMQConstants.STOCK_BACK_TAG,
                stockMessage);

        log.info("发送库存退回消息成功，orderId: {}", orderId);
    }
    private Object generateMessageKeys(Object message) {
        if (message instanceof OrderMessage) {
            return ((OrderMessage) message).getOrderId();
        } else if (message instanceof OrderTimeoutMessage) {
            return ((OrderTimeoutMessage) message).getOrderId();
        }
        return UUID.randomUUID().toString();
    }
    private int convertMinutesToDelayLevel(int minutes) {
        // RocketMQ延时级别映射
        // 1=1s, 2=5s, 3=10s, 4=30s, 5=1m, 6=2m, 7=3m, 8=4m, 9=5m, 10=6m, 11=7m, 12=8m, 13=9m, 14=10m, 15=20m, 16=30m, 17=1h, 18=2h
        if (minutes <= 1) return 5;   // 1分钟
        if (minutes <= 2) return 6;   // 2分钟
        if (minutes <= 3) return 7;   // 3分钟
        if (minutes <= 4) return 8;   // 4分钟
        if (minutes <= 5) return 9;   // 5分钟
        if (minutes <= 6) return 10;  // 6分钟
        if (minutes <= 7) return 11;  // 7分钟
        if (minutes <= 8) return 12;  // 8分钟
        if (minutes <= 9) return 13;  // 9分钟
        if (minutes <= 10) return 14; // 10分钟
        if (minutes <= 20) return 15; // 20分钟
        if (minutes <= 30) return 16; // 30分钟
        if (minutes <= 60) return 17; // 1小时
        return 18; // 2小时（最大延时）
    }
}
