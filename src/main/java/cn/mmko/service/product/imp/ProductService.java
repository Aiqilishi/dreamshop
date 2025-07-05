package cn.mmko.service.product.imp;

import cn.mmko.dao.IProductDao;
import cn.mmko.dao.IProductImageDao;
import cn.mmko.dto.product.ProductCreateDTO;
import cn.mmko.dto.product.ProductDetailDTO;
import cn.mmko.dto.product.ProductImagesDTO;
import cn.mmko.dto.product.ProductListDTO;
import cn.mmko.enums.ResponseCode;
import cn.mmko.exception.AppException;
import cn.mmko.po.ProductImagePo;
import cn.mmko.po.ProductPo;
import cn.mmko.service.product.IProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class ProductService implements IProductService {
    @Resource
    private IProductDao productDao;
    @Resource
    private IProductImageDao productImageDao;
    @Override
    public PageInfo<ProductListDTO> queryProduct(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductPo> products = productDao.queryProduct();
        List<ProductListDTO> productListDTO = new ArrayList<>();
        for (ProductPo product: products) {
            productListDTO.add(ProductListDTO.builder()
                    .productId(String.valueOf(product.getProductId()))
                    .categoryId(product.getCategoryId())
                    .productName(product.getProductName())
                    .productPrice(product.getProductPrice())
                    .productSales(product.getProductSales())
                    .productStatus(product.getProductStatus())
                    .productImage(product.getProductImage())
                    .isRecommend(product.getIsRecommend())
                    .isHot(product.getIsHot())
                    .viewCount(product.getViewCount())
                    .build()
            );
        }
        return new PageInfo<>(productListDTO);
    }

    @Override
    public ProductDetailDTO queryProductById(Long productId) {
        List<ProductImagePo> productImagesPo = productImageDao.queryProductImage(productId);
        List<ProductImagesDTO> productImagesDTO = new ArrayList<>();
        for (ProductImagePo productImagePo: productImagesPo) {
            productImagesDTO.add(ProductImagesDTO.builder()
                    .sortOrder(productImagePo.getSortOrder())
                    .productImage(productImagePo.getImageUrl())
                    .build());
        }
        ProductPo productPo = productDao.queryProductById(productId);
        if(null==productPo) throw new AppException(ResponseCode.PRODUCT_NOT_EXIST.getCode(), ResponseCode.PRODUCT_NOT_EXIST.getInfo());
        return ProductDetailDTO.builder()
                .productName(productPo.getProductName())
                .productPrice(productPo.getProductPrice())
                .productStock(productPo.getProductStock())
                .productSales(productPo.getProductSales())
                .productStatus(productPo.getProductStatus())
                .productImage(productPo.getProductImage())
                .productImages(productImagesDTO)
                .productDesc(productPo.getProductDesc())
                .build();
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
        ProductPo productPo = productDao.queryProductById(productId);
        if(null==productPo) throw new AppException(ResponseCode.PRODUCT_NOT_EXIST.getCode(), ResponseCode.PRODUCT_NOT_EXIST.getInfo());
        productDao.updateProductviewCount(productId);
    }

    @Override
    public void updateProductStock(Long productId) {
        ProductPo productPo = productDao.queryProductById(productId);
        if(null==productPo) throw new AppException(ResponseCode.PRODUCT_NOT_EXIST.getCode(), ResponseCode.PRODUCT_NOT_EXIST.getInfo());
        productDao.updateProductStock(productId);
    }

    @Override
    public void updateProduct(ProductDetailDTO productDetailDTO) {
        productDao.updateProduct(ProductPo.builder()
                .productName(productDetailDTO.getProductName())
                .productPrice(productDetailDTO.getProductPrice())
                .productStock(productDetailDTO.getProductStock())
                .productStatus(productDetailDTO.getProductStatus())
                .productDesc(productDetailDTO.getProductDesc())
                .productImage(productDetailDTO.getProductImage())
                .build()
        );
    }


}
