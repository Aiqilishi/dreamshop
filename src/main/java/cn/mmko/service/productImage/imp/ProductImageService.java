package cn.mmko.service.productImage.imp;

import cn.mmko.dao.IProductImageDao;
import cn.mmko.po.ProductImagePo;
import cn.mmko.service.productImage.IProductImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductImageService implements IProductImageService {
    @Resource
    private IProductImageDao productImageDao;


    @Override
    public List<ProductImagePo> queryProductImage(Long productId) {
        return productImageDao.queryProductImage(productId);
    }

    @Override
    public void insertProductImage(ProductImagePo build) {
        productImageDao.insertProductImage(build);

    }

    @Override
    public void deleteProductImage(Long productId, Long productImageId) {
        productImageDao.deleteProductImage(productId,productImageId);

    }

    @Override
    public void updateProductImage(ProductImagePo build) {

        productImageDao.updateProductImage(build);
    }
}
