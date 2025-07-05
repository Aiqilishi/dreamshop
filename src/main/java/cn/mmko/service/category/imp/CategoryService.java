package cn.mmko.service.category.imp;

import cn.mmko.dao.ICategoryDao;
import cn.mmko.dto.CategoryDTO;
import cn.mmko.enums.ResponseCode;
import cn.mmko.exception.AppException;
import cn.mmko.po.CategoryPo;
import cn.mmko.service.category.ICategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class CategoryService implements ICategoryService {
    @Resource
    private ICategoryDao categoryDao;
    @Override
    public void insertCategory(CategoryDTO categoryDTO) {
      CategoryPo categoryPo = categoryDao.queryCategory(categoryDTO);
      if(categoryPo != null) throw new AppException(ResponseCode.CATEGORY_EXIST.getCode(), ResponseCode.CATEGORY_EXIST.getInfo());
     Integer maxCategorySort = categoryDao.getMaxCategorySort(categoryDTO);
     if(maxCategorySort==null) maxCategorySort = 0;
     maxCategorySort = maxCategorySort + 1;
      categoryDao.insertCategory(CategoryPo.builder()
              .userId(categoryDTO.getUserId())
              .categoryName(categoryDTO.getCategoryName())
              .parentId(categoryDTO.getParentId())
              .categorySort(maxCategorySort)
              .build());
    }
}
