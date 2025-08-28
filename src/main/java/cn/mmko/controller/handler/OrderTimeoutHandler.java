package cn.mmko.controller.handler;

import cn.mmko.po.OrderPo;
import cn.mmko.service.order.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
@Slf4j
@Component
public class OrderTimeoutHandler {
    @Resource
    private StockDomainService stockDomainService;
    @Resource
    private IOrderService orderService;
    /**
     * 处理超时订单的统一方法
     * 既可以被消息消费者调用，也可以被定时任务调用
     */
    public void handleTimeoutOrder(Long orderId, String source) {
        try {
            // 幂等性检查：如果订单已经关闭或已支付，直接返回
            OrderPo order = orderService.queryOrderById(orderId);
            if (order == null || order.getOrderStatus() != 0) {
                log.info("订单{}状态已变更，跳过处理，来源: {}", orderId, source);
                return;
            }
            // 检查订单是否真的超时
            long createTime = order.getCreateTime().getTime();
            long currentTime = System.currentTimeMillis();
            long timeoutMinutes = 30; // 30分钟超时
            if (currentTime - createTime < timeoutMinutes * 60 * 1000) {
                log.info("订单{}未超时，跳过处理，来源: {}", orderId, source);
                return;
            }
            // 关闭订单
            boolean closed = orderService.changeOrderClose(orderId);
            stockDomainService.backStockByOrderId(orderId);
            log.info("订单超时处理成功，订单ID: {}, 来源: {}", orderId, source);
        } catch (Exception e) {
            log.error("处理超时订单失败，订单ID: {}, 来源: {}", orderId, source, e);
            throw e;
        }
    }
}