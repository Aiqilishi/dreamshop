package cn.mmko.controller.job;

import cn.mmko.controller.handler.OrderTimeoutHandler;
import cn.mmko.controller.mq.producer.IOrderMessageProducer;
import cn.mmko.service.order.IOrderService;
import cn.mmko.service.product.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

// TODO: 2025/8/23 可保底的定时任务
@Component
@Slf4j
public class TimeoutCloseOrderJob {
    @Resource
    private IOrderService orderService;
    @Resource
    private OrderTimeoutHandler orderTimeoutHandler;
    @Scheduled(cron="0 0 * * * ? ")
    public void exec(){
        try {
            log.info("任务：检测超时订单");
            List<Long> orderIds=orderService.queryTimeoutOrderList();
            if(null==orderIds||orderIds.isEmpty()){
                log.info("暂无超时未支付订单");
                return;
            }
            for (Long orderId: orderIds) {
                orderTimeoutHandler.handleTimeoutOrder(orderId, "定时任务");
            }
        }catch (Exception e){
            log.error("超时60分钟订单关闭失败",e);
        }
    }
}
