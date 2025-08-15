package cn.mmko.service.category.imp;

import cn.mmko.dao.ICategoryDao;
import cn.mmko.dto.CategoryCreateDTO;
import cn.mmko.dto.CategoryUpdateDTO;
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
    public void insertCategory(String categoryName, Long userId) {
        // 检查分类名称是否重复
        CategoryPo duplicateCategory = categoryDao.queryCategoryByNameAndUserId(categoryName,userId);
        if (duplicateCategory != null) {
            throw new AppException(ResponseCode.CATEGORY_EXIST.getCode(), ResponseCode.CATEGORY_EXIST.getInfo());
        }
        
        // 获取当前用户的最大排序号，新增的分类排在最后
        Integer maxCategorySort = categoryDao.getMaxCategorySort(userId);
        
        categoryDao.insertCategory(CategoryPo.builder()
                .categoryName(categoryName)
                .userId(userId)
                .categorySort(maxCategorySort == null ? 1 : maxCategorySort + 1)
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
    
    @Override
    public void updateCategory(CategoryUpdateDTO categoryUpdateDTO) {
        // 检查分类是否存在
        CategoryPo existingCategory = categoryDao.queryCategoryById(categoryUpdateDTO.getCategoryId());
        if (existingCategory == null) {
            throw new AppException(ResponseCode.CATEGORY_NOT_EXIST.getCode(), ResponseCode.CATEGORY_NOT_EXIST.getInfo());
        }
        
        // 检查分类名称是否重复（在同一用户下）
        CategoryPo duplicateCategory = categoryDao.queryCategoryByNameAndUserId(categoryUpdateDTO.getCategoryName(), existingCategory.getUserId());
        if (duplicateCategory != null && !duplicateCategory.getCategoryId().equals(categoryUpdateDTO.getCategoryId())) {
            throw new AppException(ResponseCode.CATEGORY_EXIST.getCode(), ResponseCode.CATEGORY_EXIST.getInfo());
        }
        
        categoryDao.updateCategory(CategoryPo.builder()
                .categoryId(categoryUpdateDTO.getCategoryId())
                .categoryName(categoryUpdateDTO.getCategoryName())
                .categorySort(categoryUpdateDTO.getCategorySort())
                .build());
    }
    
    @Override
    public void deleteCategory(Long categoryId) {
        // 检查分类是否存在
        CategoryPo existingCategory = categoryDao.queryCategoryById(categoryId);
        if (existingCategory == null) {
            throw new AppException(ResponseCode.CATEGORY_NOT_EXIST.getCode(), ResponseCode.CATEGORY_NOT_EXIST.getInfo());
        }
        
        // 检查分类是否被商品使用
        Integer productCount = categoryDao.getProductCountByCategoryId(categoryId);
        if (productCount > 0) {
            throw new AppException(ResponseCode.CATEGORY_IN_USE.getCode(), "该分类下还有商品，无法删除");
        }
        
        categoryDao.deleteCategory(categoryId);
    }
}
