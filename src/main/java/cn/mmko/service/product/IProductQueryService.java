package cn.mmko.service.product;

import cn.mmko.vo.ProductBaseVO;

import java.util.List;

public interface IProductQueryService {
    List<ProductBaseVO> queryProductBase(List<Long> productId);
}
