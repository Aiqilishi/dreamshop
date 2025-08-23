package cn.mmko.common;

public class RocketMQConstants {
    public static final String ORDER_TOPIC = "ORDER_TOPIC";
    public static final String ORDER_TIMEOUT_TOPIC = "ORDER_TIMEOUT_TOPIC";
    public static final String STOCK_TOPIC = "STOCK_TOPIC";

    // 订单相关标签
    public static final String ORDER_CREATE_TAG = "CREATE";
    public static final String ORDER_PAY_SUCCESS_TAG = "PAY_SUCCESS";
    public static final String ORDER_CLOSE_TAG = "CLOSE";
    public static final String ORDER_DELIVER_TAG = "DELIVER";
    public static final String ORDER_TIMEOUT_CLOSE_TAG = "TIMEOUT_CLOSE";

    // 库存相关标签
    public static final String STOCK_LOCK_TAG = "LOCK";
    public static final String STOCK_RELEASE_TAG = "RELEASE";
    public static final String STOCK_BACK_TAG = "BACK";
    // 支付相关标签
    public static final String PAYMENT_NOTIFY_TAG = "NOTIFY";

    // 生产者组
    public static final String ORDER_PRODUCER_GROUP = "order-producer-group";
    public static final String STOCK_PRODUCER_GROUP = "stock-producer-group";

    // 消费者组
    public static final String ORDER_CONSUMER_GROUP = "order-consumer-group";
    public static final String STOCK_CONSUMER_GROUP = "stock-consumer-group";
    public static final String PAYMENT_CONSUMER_GROUP = "payment-consumer-group";
}
