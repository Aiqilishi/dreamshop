package cn.mmko.controller.mq.producer;

import java.util.Map;

public interface IPaymentMessageProducer {
    void sendPaymentNotifyMessage(Map<String, String> paymentData);
}
