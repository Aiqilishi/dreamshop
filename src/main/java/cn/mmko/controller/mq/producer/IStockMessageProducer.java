package cn.mmko.controller.mq.producer;

public interface IStockMessageProducer extends IMessageProducer<Object>{
    void sendStockLockMessage(Long orderId);
    void sendStockReleaseMessage(Long orderId);
    void sendStockBackMessage(Long orderId);
}
