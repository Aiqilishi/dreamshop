package cn.mmko.controller;

import cn.mmko.dao.ICartDao;
import cn.mmko.dto.CartCreateDTO;
import cn.mmko.dto.CartUpdateDTO;
import cn.mmko.enums.ResponseCode;
import cn.mmko.response.Response;
import cn.mmko.service.cart.ICartService;
import cn.mmko.utils.JwtUtils;
import cn.mmko.vo.CartListVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/cart")
public class CartController {
    @Resource
    private ICartService cartService;

    /**
     *  添加购物车
     * @param cartCreateDTO
     * @param request
     * @return
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Response<String> insertCart(@RequestBody CartCreateDTO cartCreateDTO, HttpServletRequest  request){
        Long customerId = (Long) request.getAttribute("customerId");
          cartService.insertCart(cartCreateDTO, customerId);
          return Response.<String>builder()
                  .code(ResponseCode.SUCCESS.getCode())
                  .info(ResponseCode.SUCCESS.getInfo())
                  .data("finish")
                  .build();
    }
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public Response<PageInfo<CartListVO>> queryCartListByUserId(@RequestParam Integer pageNum, @RequestParam Integer pageSize, HttpServletRequest request){
           Long customerId = (Long) request.getAttribute("customerId");
           PageInfo<CartListVO> cartListVOS = cartService.queryCartListByUserId(pageNum, pageSize, customerId);
           return Response. <PageInfo<CartListVO>>builder()
                   .code(ResponseCode.SUCCESS.getCode())
                   .info(ResponseCode.SUCCESS.getInfo())
                   .data(cartListVOS)
                   .build();
    }
    @RequestMapping(value = "deleteCart", method = RequestMethod.POST)
    public Response<String> deleteCart(HttpServletRequest request, @RequestParam Long productId) {
        Long userId = (Long) request.getAttribute("userId");
        cartService.deleteCart(userId, productId);
        return Response. <String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data("删除成功")
                .build();
    }
@RequestMapping(value = "/updateCart", method = RequestMethod.POST)
    public Response<String> updateCart(HttpServletRequest request, @RequestBody CartUpdateDTO cartUpdateDTO) {
    Long customerId = (Long) request.getAttribute("customerId");
        cartService.updateCart(customerId, cartUpdateDTO);
        return Response. <String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data("更新成功")
                .build();
    }
    @RequestMapping(value = "/deleteAllCart", method = RequestMethod.POST)
    public Response<String> deleteAllCart(HttpServletRequest request) {
        Long customerId = (Long) request.getAttribute("customerId");
        cartService.deleteAllCart(customerId);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data("删除成功")
                .build();
    }
}
