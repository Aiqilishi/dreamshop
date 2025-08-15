package cn.mmko.dao;

import cn.mmko.dto.CategoryDTO;
import cn.mmko.po.CategoryPo;
import cn.mmko.vo.CategoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ICategoryDao {


    void insertCategory(CategoryPo categoryPo);

    Integer getMaxCategorySort(Long userId);

    CategoryVO queryCategory(CategoryDTO categoryDTO);

    List<CategoryVO> queryPuSellerCategory(Long userId);

    List<CategoryVO> queryPuCategory();

    List<CategoryVO> queryCategorySeCategory(Long userId);
    
    CategoryPo queryCategoryById(Long categoryId);
    
    CategoryPo queryCategoryByName(String categoryName);
    
    CategoryPo queryCategoryByNameAndUserId(String categoryName, Long userId);
    
    void updateCategory(CategoryPo categoryPo);
    
    void deleteCategory(Long categoryId);
    
    Integer getProductCountByCategoryId(Long categoryId);
}
