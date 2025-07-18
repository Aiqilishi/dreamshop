package cn.mmko.dao;

import cn.mmko.po.ProductImagePo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface IProductImageDao {
    List<ProductImagePo> queryProductImage(Long productId);

    void insertProductImage(ProductImagePo build);

    void deleteProductImage(@Param("productId") Long productId, @Param("productImageId") Long productImageId);

    void updateProductImage(ProductImagePo build);
}
