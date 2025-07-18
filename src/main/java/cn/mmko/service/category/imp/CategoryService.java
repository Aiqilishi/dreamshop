package cn.mmko.service.category.imp;

import cn.mmko.dao.ICategoryDao;
import cn.mmko.dto.CategoryCreateDTO;
import cn.mmko.dto.CategoryDTO;
import cn.mmko.enums.ResponseCode;
import cn.mmko.exception.AppException;
import cn.mmko.po.CategoryPo;
import cn.mmko.service.category.ICategoryService;
import cn.mmko.vo.CategoryVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Resource
    private ICategoryDao categoryDao;
    @Override
    public void insertCategory(CategoryCreateDTO categoryCreateDTO) {
        Integer maxCategorySort = categoryDao.getMaxCategorySort(CategoryDTO.builder()
                .userId(categoryCreateDTO.getUserId())
                .build());
      categoryDao.insertCategory(CategoryPo.builder()
              .categoryName(categoryCreateDTO.getCategoryName())
              .userId(categoryCreateDTO.getUserId())
              .categorySort(maxCategorySort + 1)
              .build()
      );
    }

    @Override
    public List<CategoryVO> queryPuSellerCategory(Long userId) {
         List<CategoryVO> categoryVO = categoryDao.queryPuSellerCategory(userId);
         return categoryVO;
    }

    @Override
    public List<CategoryVO> queryPuCategory() {
        return categoryDao.queryPuCategory();
    }

    @Override
    public List<CategoryVO> queryCategorySeCategory(Long userId) {
        return categoryDao.queryCategorySeCategory(userId);
    }
}
