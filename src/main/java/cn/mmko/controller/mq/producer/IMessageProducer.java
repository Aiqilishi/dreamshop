package cn.mmko.controller.mq.producer;

public interface IMessageProducer<T>{
    void sendMessage(String topic, T message);
    void sendMessage(String topic, String tag, T message);
    void sendDelayMessage(String topic,String tag, T message, int delayLevel);
    void sendTransactionMessage(String topic, T message, Object localTransactionArg);
}
