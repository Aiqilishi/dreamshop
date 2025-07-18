package cn.mmko.dao;

import cn.mmko.po.CartPo;
import cn.mmko.vo.CartListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ICartDao {
    void insertCart(CartPo cartPo);

    CartPo queryCartByUserIdAndProductId(Long userId, Long productId);

    void updateCart(CartPo cartPo);

    List<CartListVO> queryCartListByUserId(Long userId);
}
