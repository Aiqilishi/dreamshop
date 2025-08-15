package cn.mmko.service.product;

import cn.mmko.dto.OrderItemDTO;
import cn.mmko.dto.product.*;
import cn.mmko.vo.ProductImagesVO;
import cn.mmko.vo.ProductInfoVO;
import cn.mmko.vo.ProductListVO;
import cn.mmko.vo.ProductManageListVO;
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

    List<ProductImagesVO> queryProductImages(Long productId);

    void insertProductImages(ProductImagesDTO productImagesDTO, Long productId);

    void deleteProductImages(Long productId, Long productImageId);

    void updateProductImages(ProductImagesDTO productImagesDTO, Long productId);

    void deleteProduct(List<Long> productId);

   PageInfo<ProductManageListVO> queryProductBySellerId(Integer pageNum, Integer pageSize, Long sellerId);

    PageInfo<ProductListVO> queryProductBySearch(Integer pageNum, Integer pageSize, String keyword);

    PageInfo<ProductListVO> queryProductByCategoryId(Integer pageNum, Integer pageSize, Long categoryId);

    void checkProduct(List<OrderItemDTO>  items);

    void lockProductStock(List<OrderItemDTO>  items);

    void releaseProductStock(Long productId,Integer quantity);

    String queryProductMainImages(Long productId);

    void updateProductStatus(Long productId, Integer status);

    PageInfo<ProductManageListVO> queryBackgroundBySearch(Integer pageNum, Integer pageSize, Long sellerId, String keyword);

 void backProductStock(Long productId, Integer quantity);
}
