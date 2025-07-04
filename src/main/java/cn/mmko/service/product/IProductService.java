package cn.mmko.service.product;

import cn.mmko.dto.ProductDetailDTO;
import cn.mmko.dto.ProductListDTO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IProductService {
   PageInfo<ProductListDTO> queryProduct(Integer pageNum, Integer pageSize);

   ProductDetailDTO queryProductById(Long productId);

}
