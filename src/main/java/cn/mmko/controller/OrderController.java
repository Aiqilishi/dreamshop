package cn.mmko.controller;
import cn.mmko.controller.mq.producer.IOrderMessageProducer;
import cn.mmko.dto.OrderCreateDTO;
import cn.mmko.enums.ResponseCode;
import cn.mmko.response.Response;
import cn.mmko.service.order.IOrderService;
import cn.mmko.vo.OrderBackgroundListVO;
import cn.mmko.vo.OrderListVO;
import cn.mmko.vo.OrderNumberVO;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private IOrderService orderService;
    @Value("${alipay.alipay_public_key}")
    private String alipayPublicKey;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Response<String> createOrder(@RequestBody OrderCreateDTO orderCreateDTO, HttpServletRequest request) throws AlipayApiException {
        Long customerId = (Long) request.getAttribute("customerId");
        Long userId = (Long) request.getAttribute("userId");
        String form = orderService.createOrder(orderCreateDTO, customerId, userId);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(form)
                .build();
    }

    @RequestMapping(value = "/queryOrderNumber", method = RequestMethod.GET)
    public Response<OrderNumberVO> queryOrderNumber(HttpServletRequest request) {
        Long customerId = (Long) request.getAttribute("customerId");
        OrderNumberVO orderNumberVO = orderService.queryOrderNumber(customerId);
        return Response.<OrderNumberVO>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(orderNumberVO)
                .build();
    }

    /**
     * 查询顾客订单列表
     *
     * @param pageNum
     * @param pageSize
     * @param status
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryOrderList", method = RequestMethod.GET)
    public Response<PageInfo<OrderListVO>> queryOrderList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam Integer status, HttpServletRequest request) {
        Long customerId = (Long) request.getAttribute("customerId");
        PageInfo<OrderListVO> orderListVO = orderService.queryOrderList(pageNum, pageSize, customerId, status);
        return Response.<PageInfo<OrderListVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(orderListVO)
                .build();
    }

//    /**
//     * 查询后台订单列表
//     *
//     * @param pageNum
//     * @param pageSize
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/queryOrderBackgroundList", method = RequestMethod.GET)
//    public Response<PageInfo<OrderBackgroundListVO>> queryOrderBackgroundList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize, HttpServletRequest request) {
//        Long sellerId = (Long) request.getAttribute("sellerId");
//        List<String> roles = (List<String>) request.getAttribute("role");
//        if (roles.contains("ADMIN")) {
//            sellerId = null;
//        }
//        PageInfo<OrderBackgroundListVO> orderBackgroundListVO = orderService.queryOrderBackgroundList(pageNum, pageSize, sellerId);
//        return Response.<PageInfo<OrderBackgroundListVO>>builder()
//                .code(ResponseCode.SUCCESS.getCode())
//                .info(ResponseCode.SUCCESS.getInfo())
//                .data(orderBackgroundListVO)
//                .build();
//    }

    /**
     * 查询后台订单列表（支持过滤）
     *
     * @param pageNum
     * @param pageSize
     * @param status 订单状态（可选）
     * @param itemStatus 订单项状态（可选）
     * @param keyword 关键词搜索（可选）
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryOrderBackgroundList", method = RequestMethod.GET)
    public Response<PageInfo<OrderBackgroundListVO>> queryOrderBackgroundList(
            @RequestParam(defaultValue = "1") Integer pageNum, 
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer itemStatus,
            @RequestParam(required = false) String keyword,
            HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("sellerId");
        List<String> roles = (List<String>) request.getAttribute("role");
        if (roles.contains("ADMIN")) {
            sellerId = null;
        }
        PageInfo<OrderBackgroundListVO> orderBackgroundListVO = orderService.queryOrderBackgroundList(pageNum, pageSize, sellerId, status, itemStatus, keyword);
        return Response.<PageInfo<OrderBackgroundListVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(orderBackgroundListVO)
                .build();
    }

    @RequestMapping(value = "/deliverOrder", method = RequestMethod.POST)
    public Response<String> deliverOrder(@RequestParam Long orderItemId, @RequestParam Long orderId, HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("sellerId");
        orderService.deliverOrder(orderItemId, orderId);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data("finish")
                .build();
    }

    @RequestMapping(value = "alipay_notify_url", method = RequestMethod.POST)
    public String payNotify(HttpServletRequest request) throws AlipayApiException {
        log.info("支付回调，消息接收 {}", request.getParameter("trade_status"));
        if (!request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            return "false";
        }
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            params.put(name, request.getParameter(name));
        }
        String tradeNo = params.get("out_trade_no");
        String gmtPayment = params.get("gmt_payment");
        String alipayTradeNo = params.get("trade_no");

        String sign = params.get("sign");
        String content = AlipaySignature.getSignCheckContentV1(params);
        boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, alipayPublicKey, "UTF-8"); // 验证签名
        // 支付宝验签
        if (!checkSignature) {
            return "false";
        }

        // 验签通过
        log.info("支付回调，交易名称: {}", params.get("subject"));
        log.info("支付回调，交易状态: {}", params.get("trade_status"));
        log.info("支付回调，支付宝交易凭证号: {}", params.get("trade_no"));
        log.info("支付回调，商户订单号: {}", params.get("out_trade_no"));
        log.info("支付回调，交易金额: {}", params.get("total_amount"));
        log.info("支付回调，买家在支付宝唯一id: {}", params.get("buyer_id"));
        log.info("支付回调，买家付款时间: {}", params.get("gmt_payment"));
        log.info("支付回调，买家付款金额: {}", params.get("buyer_pay_amount"));
        log.info("支付回调，支付回调，更新订单 {}", tradeNo);
        orderService.changeOrderPaySuccess(Long.valueOf(tradeNo));
        orderService.releaseProductStock(Long.valueOf(tradeNo));
        return "success";
    }
}
