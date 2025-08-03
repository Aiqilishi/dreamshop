package cn.mmko.service.order.imp;
import cn.hutool.core.util.IdUtil;
import cn.mmko.dao.IOrderDao;
import cn.mmko.dto.OrderCreateDTO;
import cn.mmko.dto.OrderItemDTO;
import cn.mmko.po.OrderItemPo;
import cn.mmko.po.OrderPo;
import cn.mmko.po.SellerPo;
import cn.mmko.service.order.IOrderService;
import cn.mmko.service.orderitem.IOrderItemService;
import cn.mmko.service.product.IProductService;
import cn.mmko.service.seller.ISellerService;
import cn.mmko.service.user.IUserService;
import cn.mmko.vo.OrderItemVO;
import cn.mmko.vo.OrderListBySellerVO;
import cn.mmko.vo.OrderListVO;
import cn.mmko.vo.OrderNumberVO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.OrderItem;
import com.alipay.api.request.AlipayTradeMergeCreateRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService implements IOrderService {
    @Value("${alipay.notify_url}")
    private String notifyUrl;
    @Resource
    private AlipayClient alipayClient;
    @Resource
    private IUserService userService;
    @Resource
    private ISellerService sellerService;
    @Resource
    private IProductService productService;
    @Resource
    private IOrderDao orderDao;
    @Resource
    private IOrderItemService orderItemService;
    /**
     * 创建订单
     * @param orderCreateDTO
     * @param userId
     * @return
     */
    @Override
    public String createOrder(OrderCreateDTO orderCreateDTO, Long userId) throws AlipayApiException {
        userService.checkUserStatus(userId);//检测用户状态
        productService.checkProduct(orderCreateDTO.getOrderItemDTOS());//检测商品状态
        sellerService.checkSellerStatus(orderCreateDTO.getOrderItemDTOS());//检测商家状态
        productService.lockProductStock(orderCreateDTO.getOrderItemDTOS());//锁定商品库存
        String productName = orderCreateDTO.getOrderItemDTOS().size() == 1
                ? orderCreateDTO.getOrderItemDTOS().get(0).getProductName()
                : orderCreateDTO.getOrderItemDTOS().get(0).getProductName() + "等" + orderCreateDTO.getOrderItemDTOS().size() + "件商品";
        Long orderId = IdUtil.getSnowflake().nextId();
        OrderPo orderPo = OrderPo.builder()
                .orderId(orderId)
                .userId(userId)
                .orderStatus(0)
                .totalAmount(orderCreateDTO.getTotalAmount())
                .build();
        orderDao.insertOrder(orderPo);
        for(OrderItemDTO orderItemDTO:orderCreateDTO.getOrderItemDTOS()){
            orderItemService.createOrderItem(OrderItemPo.builder()
                    .orderId(orderId)
                    .productId(orderItemDTO.getProductId())
                    .sellerId(orderItemDTO.getSellerId())
                    .productName(orderItemDTO.getProductName())
                    .productPrice(orderItemDTO.getPrice())
                    .quantity(orderItemDTO.getQuantity())
                    .build());
        }
        return doPrepayOrder(productName,orderId,orderCreateDTO.getTotalAmount());
    }

    @Override
    public void changeOrderPaySuccess(Long orderId) {
        orderDao.updateOrderPaySuccess(orderId);
    }

    @Override
    public void releaseProductStock(Long orderId) {
        List<OrderItemPo> orderItemPos = orderItemService.queryOrderItemByOrderId(orderId);
        for(OrderItemPo orderItemPo:orderItemPos){
            productService.releaseProductStock(orderItemPo.getProductId(),orderItemPo.getQuantity());
        }
    }

    @Override
    public List<String> queryTimeoutOrderList() {
        return orderDao.queryTimeoutOrderList();
    }

    @Override
    public boolean changeOrderClose(String orderId) {
        return orderDao.updateOrderClose(orderId);
    }

    @Override
    public OrderNumberVO queryOrderNumber(Long userId) {
        return orderDao.queryOrderNumber(userId);
    }

    @Override
    public PageInfo<OrderListVO> queryOrderList(Integer pageNum, Integer pageSize, Long userId,Integer  status) {
        PageHelper.startPage(pageNum, pageSize);
        List<OrderPo> orderPos = orderDao.queryOrderList(userId,status);
        List<OrderListVO> orderListVOs = new ArrayList<>();
        for (OrderPo orderPo : orderPos) {
            List<OrderItemPo> orderItemPos = orderItemService.queryOrderItemByOrderId(orderPo.getOrderId());
            Map<Long,List<OrderItemPo>> orderItemMap = orderItemPos.stream().collect(Collectors.groupingBy(OrderItemPo::getSellerId));
            List<OrderListBySellerVO> orderListBySellerVOS = new ArrayList<>();
            for (Map.Entry<Long, List<OrderItemPo>> entry : orderItemMap.entrySet()) {
                Long sellerId = entry.getKey();
                List<OrderItemPo> orderItemVos = entry.getValue();
                OrderListBySellerVO orderListBySellerVO = buildSellerVO(sellerId, orderItemVos);
                 orderListBySellerVOS.add(orderListBySellerVO);
            }
            orderListVOs.add(OrderListVO.builder()
                    .orderId(orderPo.getOrderId())
                    .totalPrice(orderPo.getTotalAmount())
                    .createTime(orderPo.getCreateTime())
                    .payTime(orderPo.getPayTime())
                    .deliveryTime(orderPo.getDeliveryTime())
                    .finishTime(orderPo.getFinishTime())
                    .orderListBySellerVOList(orderListBySellerVOS)
                    .build());
        }
        return new PageInfo<>(orderListVOs);
    }
    private OrderListBySellerVO buildSellerVO(Long sellerId, List<OrderItemPo> items) {
        // 查询商家信息
        SellerPo seller = sellerService.querySellerById(sellerId);

        // 计算商家商品总金额
        BigDecimal sellerAmount = items.stream()
                .map(item -> item.getProductPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // 构建商品VO列表
        List<OrderItemVO> itemVOList = items.stream()
                .map(this::buildItemVO)
                .collect(Collectors.toList());

        return OrderListBySellerVO.builder()
                .sellerId(sellerId)
                .sellerName(seller.getSellerName())
                .totalPrice(sellerAmount)
                .orderItemVOList(itemVOList)
                .build();
    }
    private OrderItemVO buildItemVO(OrderItemPo item) {
        return OrderItemVO.builder()
                .itemId(item.getItemId())
                .productId(item.getProductId())
                .productName(item.getProductName())
                .productImage(productService.queryProductMainImages(item.getProductId()))
                .price(item.getProductPrice())
                .quantity(item.getQuantity())
                .totalPrice(item.getProductPrice().multiply(new BigDecimal(item.getQuantity())))
                .status(item.getItemStatus())
                .build();
    }

    private String doPrepayOrder( String productName, Long orderId, BigDecimal totalAmount) throws AlipayApiException {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(notifyUrl);
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderId);
        bizContent.put("total_amount", totalAmount.toString());
        bizContent.put("subject", productName);
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(bizContent.toString());
        return alipayClient.pageExecute(request).getBody();

    }
    private String doMutiPrepayOrder( String productName, OrderCreateDTO orderCreateDTO, Long orderId) throws AlipayApiException {
        AlipayTradeMergeCreateRequest request = new AlipayTradeMergeCreateRequest();
        request.setNotifyUrl(notifyUrl);
        String buyer_id="2088722062932922";
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_merge_no", orderId); // 合并订单号
        bizContent.put("buyer_id", buyer_id);
        // 构建子订单列表
        JSONArray orderDetails = new JSONArray();
        for (OrderItemDTO item : orderCreateDTO.getOrderItemDTOS()) {
            JSONObject orderDetail = new JSONObject();
            orderDetail.put("app_id","2021000147646288");
            orderDetail.put("out_trade_no", orderId + "_" + item.getProductId()); // 子订单号
            orderDetail.put("total_amount", item.getPrice().multiply(new BigDecimal(item.getQuantity())).toString());
            orderDetail.put("subject", item.getProductName());
            orderDetail.put("product_code", "FAST_INSTANT_TRADE_PAY");
            orderDetails.add(orderDetail);
        }
        bizContent.put("op_app_id","2021000147645784");
        bizContent.put("order_details", orderDetails);
//        bizContent.put("total_mount", orderCreateDTO.getTotalAmount().toString());
        request.setBizContent(bizContent.toString());
        return alipayClient.pageExecute(request)
                .getBody();

    }
}
