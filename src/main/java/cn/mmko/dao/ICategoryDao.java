package cn.mmko.dao;

import cn.mmko.dto.CategoryDTO;
import cn.mmko.po.CategoryPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICategoryDao {
    CategoryPo queryCategory(CategoryDTO categoryDTO);

    void insertCategory(CategoryPo build);

    Integer getMaxCategorySort(CategoryDTO categoryDTO);
}
