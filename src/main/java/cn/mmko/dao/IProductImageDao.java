package cn.mmko.dao;

import cn.mmko.po.ProductImagePo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface IProductImageDao {
    List<ProductImagePo> queryProductImage(Long productId);
}
