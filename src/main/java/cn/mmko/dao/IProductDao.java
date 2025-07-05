package cn.mmko.dao;

import cn.mmko.dto.product.ProductCreateDTO;
import cn.mmko.po.ProductPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IProductDao{

    List<ProductPo> queryProduct();

    ProductPo queryProductById(Long productId);

    ProductPo queryExitProduct(ProductCreateDTO productCreateDTO);

    void insertProduct(ProductPo build);

    void updateProductviewCount(Long productId);

    void updateProductStock(Long productId);
}
