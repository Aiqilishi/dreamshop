package cn.mmko.service.cart;

import cn.mmko.dto.CartCreateDTO;
import cn.mmko.dto.CartUpdateDTO;
import cn.mmko.vo.CartListVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICartService {
    void insertCart(CartCreateDTO cartCreateDTO, Long userId);

   PageInfo<CartListVO> queryCartListByUserId(Integer pageNum, Integer pageSize,Long userId);

    void deleteCart(Long userId, Long productId);

    void updateCart(Long userId, CartUpdateDTO cartUpdateDTO);

    void deleteAllCart(Long userId);
}
