package cn.mmko.controller.job;

import cn.mmko.controller.mq.producer.IOrderMessageProducer;
import cn.mmko.controller.mq.producer.IStockMessageProducer;
import cn.mmko.service.order.IOrderService;
import cn.mmko.service.product.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

// TODO: 2025/8/23 可保底的定时任务
//@Component
@Slf4j
public class TimeoutCloseOrderJob {
//    @Resource
//    private IOrderService orderService;
//    @Resource
//    private IOrderMessageProducer orderMessageProducer;
//    @Resource
//    private IStockMessageProducer stockMessageProducer;
//    @Scheduled(cron="0/5 * * * * ? ")
//    public void exec(){
//        try {
//            log.info("任务：检测超时订单");
//            List<Long> orderIds=orderService.queryTimeoutOrderList();
//            if(null==orderIds||orderIds.isEmpty()){
//                log.info("暂无超时未支付订单");
//                return;
//            }
//            for (Long orderId: orderIds) {
////                boolean status = orderService.changeOrderClose(orderId);
//                orderMessageProducer.sendOrderCloseMessage(orderId);
////                orderService.backProductStock(orderId);
//                stockMessageProducer.sendStockBackMessage(orderId);
//                log.info("已关闭的超时未支付的订单 orderId:{} ",orderId);
//
//            }
//        }catch (Exception e){
//            log.error("超时15分钟订单关闭失败",e);
//        }
//    }
}
