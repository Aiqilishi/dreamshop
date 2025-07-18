package cn.mmko.service.product;

import cn.mmko.dto.product.*;
import cn.mmko.po.ProductPo;
import cn.mmko.vo.ProductImagesVO;
import cn.mmko.vo.ProductInfoVO;
import cn.mmko.vo.ProductListVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IProductService {
    /**
     * 进入商城查询商品列表
     * @param pageNum
     * @param pageSize
     * @return
     */
   PageInfo<ProductListVO> queryProduct(Integer pageNum, Integer pageSize);

   ProductInfoVO queryProductById(Long productId);

    void insertProduct(ProductCreateDTO productCreateDTO);

    void updateProductviewCount(Long productId);

    void updateProductStock(Long productId);

    void updateProduct(ProductUpdateDTO productUpdateDTO);

    List<ProductImagesDTO> queryProductImages(Long productId);

    void insertProductImages(ProductImagesDTO productImagesDTO, Long productId);

    void deleteProductImages(Long productId, Long productImageId);

    void updateProductImages(ProductImagesDTO productImagesDTO, Long productId);

    void deleteProduct(Long productId);

   PageInfo<ProductManageDTO> queryProductBySellerId(Integer pageNum, Integer pageSize, Long sellerId);

    PageInfo<ProductListVO> queryProductBySearch(Integer pageNum, Integer pageSize, String keyword);

    PageInfo<ProductListVO> queryProductByCategoryId(Integer pageNum, Integer pageSize, Long categoryId);
}
