package cn.mmko.controller.mq.producer.imp;

import cn.mmko.controller.mq.producer.IPaymentMessageProducer;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
@Slf4j
public class PaymentMessageProducer implements IPaymentMessageProducer {

@Resource
private RocketMQTemplate rocketMQTemplate;
    @Override
    public void sendPaymentNotifyMessage(Map<String, String> paymentData) {
        try {
            String messageBody = JSON.toJSONString(paymentData);
            rocketMQTemplate.convertAndSend(
                    "PAYMENT_TOPIC",

                    messageBody
            );
            log.info("支付通知消息发送成功，订单ID: {}", paymentData.get("orderId"));
        } catch (Exception e) {
            log.error("支付通知消息发送失败，支付数据: {}", paymentData, e);
            throw e;
        }
    }
}
