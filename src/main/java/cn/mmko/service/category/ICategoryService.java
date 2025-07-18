package cn.mmko.service.category;

import cn.mmko.dto.CategoryCreateDTO;
import cn.mmko.dto.CategoryDTO;
import cn.mmko.vo.CategoryVO;

import java.util.List;

public interface ICategoryService {
    void insertCategory(CategoryCreateDTO categoryCreateDTO);

    List< CategoryVO> queryPuSellerCategory(Long sellerId);

    List<CategoryVO>  queryPuCategory();

    List<CategoryVO> queryCategorySeCategory(Long userId);
}
