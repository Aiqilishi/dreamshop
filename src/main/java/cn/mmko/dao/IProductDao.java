package cn.mmko.dao;

import cn.mmko.dto.product.ProductCreateDTO;
import cn.mmko.dto.product.ProductManageDTO;
import cn.mmko.po.ProductPo;
import cn.mmko.vo.ProductBaseVO;
import cn.mmko.vo.ProductInfoVO;
import cn.mmko.vo.ProductListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IProductDao{

    List<ProductListVO> queryProduct();

    ProductInfoVO queryProductById(Long productId);

    ProductPo queryExitProduct(ProductCreateDTO productCreateDTO);

    Long queryProductNumbySellerId(Long userId);

    void insertProduct(ProductPo build);

    void updateProductviewCount(Long productId);

    void updateProductStock(Long productId);

    void updateProduct(ProductPo build);

    void deleteProduct(Long productId);

    List<ProductPo> queryProductBySellerId(Long userId);

    /**
     *  搜索商品
     * @param keyword
     * @return
     */
    List<ProductListVO> queryProductBySearch(String keyword);

    /**
     *  分类搜索
     * @param categoryId
     * @return
     */
    List<ProductListVO> queryProductByCategoryId(Long categoryId);

    List<ProductBaseVO> queryProductBase(List<Long> productIds);
}
