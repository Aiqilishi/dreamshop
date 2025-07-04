package cn.mmko.dao;

import cn.mmko.po.ProductPo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IProductDao{

    List<ProductPo> queryProduct();

    ProductPo queryProductById(Long productId);

}
