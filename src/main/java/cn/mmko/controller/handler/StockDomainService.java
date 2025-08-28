package cn.mmko.controller.handler;

import cn.mmko.po.OrderItemPo;
import cn.mmko.service.orderitem.IOrderItemService;
import cn.mmko.service.product.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class StockDomainService {
    @Resource
    private IOrderItemService orderItemService;
    @Resource
    private IProductService productService;

    /**
     * 根据订单ID释放库存
     */
    public void releaseStockByOrderId(Long orderId) {
        List<OrderItemPo> orderItems = orderItemService.queryOrderItemByOrderId(orderId);

        for (OrderItemPo item : orderItems) {
            productService.releaseProductStock(item.getProductId(), item.getQuantity());
        }

        log.info("订单库存释放完成，订单ID: {}", orderId);
    }

    /**
     * 根据订单ID退回库存
     */
    public void backStockByOrderId(Long orderId) {
        List<OrderItemPo> orderItems = orderItemService.queryOrderItemByOrderId(orderId);

        for (OrderItemPo item : orderItems) {
            productService.backProductStock(item.getProductId(), item.getQuantity());
        }

        log.info("订单库存退回完成，订单ID: {}", orderId);
    }
}
