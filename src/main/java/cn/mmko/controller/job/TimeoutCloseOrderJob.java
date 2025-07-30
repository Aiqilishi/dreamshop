package cn.mmko.controller.job;

import cn.mmko.service.order.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class TimeoutCloseOrderJob {
    @Resource
    private IOrderService orderService;
    @Scheduled(cron="0/5 * * * * ? ")
    public void exec(){
        try {
            log.info("任务：检测超时订单");
            List<String> orderIds=orderService.queryTimeoutOrderList();
            if(null==orderIds||orderIds.isEmpty()){
                log.info("暂无超时未支付订单");
                return;
            }
            for (String orderId: orderIds) {
                boolean status = orderService.changeOrderClose(orderId);
                orderService.releaseProductStock(Long.valueOf(orderId));
                log.info("已关闭的超时未支付的订单 orderId:{} status:{}",orderId,status);

            }
        }catch (Exception e){
            log.error("超时15分钟订单关闭失败",e);
        }
    }
}
