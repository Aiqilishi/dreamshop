package cn.mmko.service.product.imp;

import cn.mmko.dao.ICategoryDao;
import cn.mmko.dao.IProductDao;
import cn.mmko.service.product.IProductQueryService;
import cn.mmko.vo.ProductBaseVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductQueryService implements IProductQueryService {
    @Resource
    private IProductDao productDao;
    @Override
    public List<ProductBaseVO> queryProductBase(List<Long> productIds) {
        return productDao.queryProductBase(productIds);
    }
}
