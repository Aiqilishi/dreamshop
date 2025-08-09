package cn.mmko.service.productImage;

import cn.mmko.po.ProductImagePo;

import java.util.List;

public interface IProductImageService {
    List<ProductImagePo> queryProductImage(Long productId);

    void insertProductImage(ProductImagePo build);

    void deleteProductImage(Long productId, Long productImageId);

    void updateProductImage(ProductImagePo build);
}
