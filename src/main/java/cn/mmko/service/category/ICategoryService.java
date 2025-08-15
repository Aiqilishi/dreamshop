package cn.mmko.service.category;

import cn.mmko.dto.CategoryCreateDTO;
import cn.mmko.dto.CategoryUpdateDTO;
import cn.mmko.dto.CategoryDTO;
import cn.mmko.vo.CategoryVO;

import java.util.List;

public interface ICategoryService {
    void insertCategory(String categoryName, Long sellerId);
    
    void updateCategory(CategoryUpdateDTO categoryUpdateDTO);
    
    void deleteCategory(Long categoryId);

    List< CategoryVO> queryPuSellerCategory(Long sellerId);

    List<CategoryVO>  queryPuCategory();

    List<CategoryVO> queryCategorySeCategory(Long userId);
}
