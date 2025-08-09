package cn.mmko.service.product.imp;

import cn.mmko.dao.IProductDao;
import cn.mmko.dto.OrderItemDTO;
import cn.mmko.dto.product.*;
import cn.mmko.enums.ResponseCode;
import cn.mmko.exception.AppException;
import cn.mmko.po.ProductImagePo;
import cn.mmko.po.ProductPo;
import cn.mmko.service.product.IProductService;
import cn.mmko.service.productImage.IProductImageService;
import cn.mmko.vo.ProductImagesVO;
import cn.mmko.vo.ProductInfoVO;
import cn.mmko.vo.ProductListVO;
import cn.mmko.vo.ProductManageListVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {
    @Resource
    private IProductDao productDao;
    @Resource
    private IProductImageService productImageService;
    @Override
    public PageInfo<ProductListVO> queryProduct(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductListVO> products = productDao.queryProduct();
        return new PageInfo<>(products);
    }

    @Override
    public ProductInfoVO queryProductById(Long productId) {
        List<ProductImagePo> productImagesPo = productImageService.queryProductImage(productId);
        List<ProductImagesVO> productImagesVOS= new ArrayList<>();
        for (ProductImagePo productImagePo: productImagesPo) {
            productImagesVOS.add(ProductImagesVO.builder()
                    .sortOrder(productImagePo.getSortOrder())
                            .imageId(productImagePo.getImageId())
                            .imageUrl(productImagePo.getImageUrl())
                    .build());
        }
        ProductInfoVO productInfoVO = productDao.queryProductById(productId);
        if(null==productInfoVO) throw new AppException(ResponseCode.PRODUCT_NOT_EXIST.getCode(), ResponseCode.PRODUCT_NOT_EXIST.getInfo());
        productInfoVO.setProductImages(productImagesVOS);
        return productInfoVO;
    }

    @Override
    public void insertProduct(ProductCreateDTO productCreateDTO) {
        ProductPo productPo = productDao.queryExitProduct(productCreateDTO);
        if(null!=productPo) throw new AppException(ResponseCode.PRODUCT_EXIST.getCode(), ResponseCode.PRODUCT_EXIST.getInfo());

        productDao.insertProduct(ProductPo.builder()
                .productName(productCreateDTO.getProductName())
                .categoryId(productCreateDTO.getCategoryId())
                .userId(productCreateDTO.getUserId())
                .productPrice(productCreateDTO.getProductPrice())
                .productStock(productCreateDTO.getProductStock())
                .productDesc(productCreateDTO.getProductDesc())
                .productImage(productCreateDTO.getProductImage())
                .build()
        );
    }

    @Override
    public void updateProductviewCount(Long productId) {
        ProductInfoVO productPo = productDao.queryProductById(productId);
        if(null==productPo) throw new AppException(ResponseCode.PRODUCT_NOT_EXIST.getCode(), ResponseCode.PRODUCT_NOT_EXIST.getInfo());
        productDao.updateProductviewCount(productId);
    }

    @Override
    public void updateProductStock(Long productId) {
        ProductInfoVO productPo = productDao.queryProductById(productId);
        if(null==productPo) throw new AppException(ResponseCode.PRODUCT_NOT_EXIST.getCode(), ResponseCode.PRODUCT_NOT_EXIST.getInfo());
        productDao.updateProductStock(productId);
    }

    @Override
    public void updateProduct(ProductUpdateDTO productUpdateDTO) {
        ProductInfoVO productPo = productDao.queryProductById(productUpdateDTO.getProductId());
        if(null==productPo) throw new AppException(ResponseCode.PRODUCT_NOT_EXIST.getCode(), ResponseCode.PRODUCT_NOT_EXIST.getInfo());
        productDao.updateProduct(ProductPo.builder()
                .productId(productUpdateDTO.getProductId())
                .productName(productUpdateDTO.getProductName())
                .productPrice(productUpdateDTO.getProductPrice())
                .categoryId(productUpdateDTO.getCategoryId())
                .productStock(productUpdateDTO.getProductStock())
                .productDesc(productUpdateDTO.getProductDesc())
                .productImage(productUpdateDTO.getProductImage())
                        .isRecommend(productUpdateDTO.getIsRecommend())
                .build()
        );
    }

    /**
     * 查询商品子图
     * @param productId
     * @return
     */
    @Override
    public List<ProductImagesVO> queryProductImages(Long productId) {
       List<ProductImagePo> productImagesPo =  productImageService.queryProductImage(productId);
       if(null==productImagesPo) throw new AppException(ResponseCode.IMAGES_NOT_EXIST.getCode(), ResponseCode.IMAGES_NOT_EXIST.getInfo());
       List<ProductImagesVO> productImagesVOS = new ArrayList<>();
       for (ProductImagePo productImagePo: productImagesPo) {
           productImagesVOS.add(ProductImagesVO.builder()
                   .imageId(productImagePo.getImageId())
                   .imageUrl(productImagePo.getImageUrl())
                   .sortOrder(productImagePo.getSortOrder())
                   .build());
       }
       return productImagesVOS;
    }

    @Override
    public void insertProductImages(ProductImagesDTO productImagesDTO, Long productId) {
        productImageService.insertProductImage(ProductImagePo.builder()
                .productId(productId)
                .imageUrl(productImagesDTO.getProductImage())
                .sortOrder(productImagesDTO.getSortOrder())
                .build());
    }

    @Override
    public void deleteProductImages(Long productId, Long productImageId) {
        productImageService.deleteProductImage(productId,productImageId);
    }

    @Override
    public void updateProductImages(ProductImagesDTO productImagesDTO, Long productId) {
        productImageService.updateProductImage(ProductImagePo.builder()
                .imageId(productImagesDTO.getProductImageId())
                .productId(productId)
                .imageUrl(productImagesDTO.getProductImage())
                .sortOrder(productImagesDTO.getSortOrder())
                .build());
    }
    @Override
    public void deleteProduct(List<Long> productIds) {
        productDao.deleteProduct(productIds);
    }

    @Override
    public PageInfo<ProductManageListVO> queryProductBySellerId(Integer pageNum, Integer pageSize, Long sellerId) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductManageListVO> productManageDTOS = productDao.queryProductBySellerId(sellerId);
        return new PageInfo<>(productManageDTOS);
    }

    /**
     *  搜索
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    @Override
    public PageInfo<ProductListVO> queryProductBySearch(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductListVO> products = productDao.queryProductBySearch(keyword);
        return new PageInfo<>(products);
    }

    @Override
    public PageInfo<ProductListVO> queryProductByCategoryId(Integer pageNum, Integer pageSize, Long categoryId) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductListVO> products = productDao.queryProductByCategoryId(categoryId);
        return new PageInfo<>(products);
    }

    @Override
    public void checkProduct(List<OrderItemDTO> items) {
        Map<Long,Integer> productIdAndQuantity = items.stream().collect(Collectors.toMap(OrderItemDTO::getProductId,OrderItemDTO::getQuantity));
        Map<Long, BigDecimal> productIdAndPrice = items.stream().collect(Collectors.toMap(OrderItemDTO::getProductId,OrderItemDTO::getPrice));
        List<Long> productIds = new ArrayList<>(productIdAndQuantity.keySet());
        List<ProductPo> productPos = productDao.queryCheckProductById(productIds);
        // 将查询结果转换为Map，便于查找
        Map<Long, ProductPo> productMap = productPos.stream()
                .collect(Collectors.toMap(ProductPo::getProductId, product -> product));
        // 检查每个商品是否存在
        for (Long productId : productIds) {
            ProductPo productPo = productMap.get(productId);
            if (productPo == null) {
                throw new AppException(ResponseCode.PRODUCT_NOT_EXIST.getCode(), 
                        "商品不存在，商品ID: " + productId);
            }
            Integer quantity = productIdAndQuantity.get(productId);
            BigDecimal price = productIdAndPrice.get(productId);
            
            if(productPo.getProductStatus() == 0){
                throw new AppException(ResponseCode.PRODUCT_NOT_EXIST.getCode(), "商品下架，商品ID: "+productId);
            }
            if(productPo.getProductStock()-productPo.getFrozenStock()<quantity){
                throw new AppException(ResponseCode.PRODUCT_NOT_ENOUGH.getCode(), ResponseCode.PRODUCT_NOT_ENOUGH.getInfo());
            }
            if (productPo.getProductPrice().compareTo(price)!=0){
                throw new AppException(ResponseCode.PRICE_ERROR.getCode(), ResponseCode.PRICE_ERROR.getInfo());
            }
        }
    }

    @Override
    public void lockProductStock(List<OrderItemDTO>  items) {
        for (OrderItemDTO item: items){
            productDao.lockProductStock(item.getProductId(),item.getQuantity());
        }
    }

    @Override
    public void releaseProductStock(Long productId,Integer quantity) {
           productDao.releaseProductStock(productId,quantity);
    }

    @Override
    public String queryProductMainImages(Long productId) {
        return productDao.queryProductMainImages(productId);
    }

    @Override
    public void updateProductStatus(Long productId) {
        productDao.updateProductStatus(productId);
    }

    /**
     * 后台搜索
     * @param pageNum
     * @param pageSize
     * @param sellerId
     * @param keyword
     * @return
     */
    @Override
    public PageInfo<ProductManageListVO> queryBackgroundBySearch(Integer pageNum, Integer pageSize, Long sellerId, String keyword) {
         PageHelper.startPage(pageNum, pageSize);
         List<ProductManageListVO> productManageDTOS = productDao.queryBackgroundBySearch(sellerId,keyword);
         return new PageInfo<>(productManageDTOS);
    }


}
