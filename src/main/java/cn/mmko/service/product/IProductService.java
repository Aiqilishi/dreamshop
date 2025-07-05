package cn.mmko.service.product;

import cn.mmko.dto.product.ProductCreateDTO;
import cn.mmko.dto.product.ProductDetailDTO;
import cn.mmko.dto.product.ProductListDTO;
import com.github.pagehelper.PageInfo;

public interface IProductService {
   PageInfo<ProductListDTO> queryProduct(Integer pageNum, Integer pageSize);

   ProductDetailDTO queryProductById(Long productId);

    void insertProduct(ProductCreateDTO productCreateDTO);

    void updateProductviewCount(Long productId);

    void updateProductStock(Long productId);

    void updateProduct(ProductDetailDTO productDetailDTO);
}
