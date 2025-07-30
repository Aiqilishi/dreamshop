package cn.mmko.service.cart.imp;

import cn.mmko.dao.ICartDao;
import cn.mmko.dao.IProductDao;
import cn.mmko.dto.CartCreateDTO;
import cn.mmko.dto.CartUpdateDTO;
import cn.mmko.po.CartPo;
import cn.mmko.redis.IRedisService;
import cn.mmko.service.cart.ICartService;
import cn.mmko.service.product.IProductQueryService;
import cn.mmko.service.product.IProductService;
import cn.mmko.vo.CartListVO;
import cn.mmko.vo.ProductBaseVO;
import cn.mmko.vo.ProductListVO;
import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.redisson.api.RMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService {
    @Resource
    private ICartDao cartDao;
    @Resource
    private IRedisService redisService;
    @Resource
    private IProductQueryService productQueryService;
    @Override
    public void insertCart(CartCreateDTO cartCreateDTO, Long userId) {
        String key = "cart:" + userId;
        String filed = cartCreateDTO.getProductId().toString();
        String cartValue = redisService.getFromMap(key, filed);
        CartCreateDTO redisCart;
        if (cartValue != null) {
               redisCart = JSON.parseObject(cartValue, CartCreateDTO.class);
               redisCart.setQuantity(redisCart.getQuantity() + cartCreateDTO.getQuantity());
               redisService.addToMap(key, filed, JSON.toJSONString(redisCart));
        }else {
            redisService.addToMap(key, filed, JSON.toJSONString(cartCreateDTO));
        }
//        CartPo cartPo = cartDao.queryCartByUserIdAndProductId(userId, cartCreateDTO.getProductId());
//        if (cartPo != null) {
//            cartPo.setQuantity(cartPo.getQuantity() + cartCreateDTO.getQuantity());
//            cartDao.updateCart(cartPo);
//        }else {
//            cartDao.insertCart(CartPo.builder()
//                    .userId(userId)
//                    .productId(cartCreateDTO.getProductId())
//                    .quantity(cartCreateDTO.getQuantity())
//                    .build());
//        }
    }

    @Override
    public PageInfo<CartListVO> queryCartListByUserId(Integer pageNum, Integer pageSize, Long userId) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<CartListVO> cartListVOS = cartDao.queryCartListByUserId(userId);
//        return new PageInfo<>(cartListVOS);
        String key = "cart:" + userId;
        // 1. 从Redis取出所有购物车商品
        List<CartListVO> cartList = new ArrayList<>();
        RMap<String, String> map = redisService.getMap(key);
        List<Long> productIds = map.keySet().stream().map(Long::parseLong).collect(Collectors.toList());
        if(productIds.isEmpty()) return new PageInfo<>(cartList);
        List<ProductBaseVO> productBaseVOS = productQueryService.queryProductBase(productIds);
        Map<Long, ProductBaseVO> productBaseVOMap = productBaseVOS.stream().collect(Collectors.toMap(ProductBaseVO::getProductId, productBaseVO -> productBaseVO));

        for (String value : map.values()) {
            CartCreateDTO cartCreateDTO = JSON.parseObject(value, CartCreateDTO.class);
            CartListVO cartListVO = CartListVO.builder()
                    .productId(cartCreateDTO.getProductId())
                    .sellerId(productBaseVOMap.get(cartCreateDTO.getProductId()).getUserId())
                    .productName(productBaseVOMap.get(cartCreateDTO.getProductId()).getProductName())
                    .productImage(productBaseVOMap.get(cartCreateDTO.getProductId()).getProductImage())
                    .productPrice(productBaseVOMap.get(cartCreateDTO.getProductId()).getProductPrice())
                    .quantity(cartCreateDTO.getQuantity())
                    .build();
            cartList.add(cartListVO);
        }
        // 2. 内存分页
        int total = cartList.size();
        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, total);
        List<CartListVO> pageList = fromIndex >= total ? new ArrayList<>() : cartList.subList(fromIndex, toIndex);

        // 3. 构造PageInfo
        PageInfo<CartListVO> pageInfo = new PageInfo<>(pageList);
        pageInfo.setTotal(total);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        return pageInfo;
    }

    @Override
    public void deleteCart(Long userId, Long productId) {
        String key = "cart:" + userId;
        redisService.getMap( key).remove(productId.toString());
    }

    @Override
    public void updateCart(Long userId, CartUpdateDTO cartUpdateDTO) {
//        String key = "cart:" + userId;
//        redisService.getMap(key).put(cartUpdateDTO.getProductId().toString(),cartUpdateDTO.getQuantity().toString());
        String key = "cart:" + userId;
        String field = cartUpdateDTO.getProductId().toString();

        // 先查出原有的 CartCreateDTO
        String cartJson = (String) redisService.getMap(key).get(field);
        CartCreateDTO cartCreateDTO;
        if (cartJson != null) {
            cartCreateDTO = JSON.parseObject(cartJson, CartCreateDTO.class);
            cartCreateDTO.setQuantity(cartUpdateDTO.getQuantity());
        } else {
            // 如果没有，说明是新加的
            cartCreateDTO = new CartCreateDTO();
            cartCreateDTO.setProductId(cartUpdateDTO.getProductId());
            cartCreateDTO.setQuantity(cartUpdateDTO.getQuantity());
        }
        // 存回完整的 JSON 字符串
        redisService.getMap(key).put(field, JSON.toJSONString(cartCreateDTO));
    }

    @Override
    public void deleteAllCart(Long userId) {
        String key = "cart:" + userId;
        redisService.remove(key);
    }
}
