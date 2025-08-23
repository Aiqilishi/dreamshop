package cn.mmko.controller.mq.producer;

public interface IOrderMessageProducer extends IMessageProducer<Object>{
    void sendOrderCreateMessage(Long orderId);
    void sendOrderPaySuccessMessage(Long orderId);
    void sendOrderCloseMessage(Long orderId);
    void sendOrderDeliverMessage(Long orderId);
    void sendOrderTimeoutCloseMessage(Long orderId, int delayMinutes);
}
