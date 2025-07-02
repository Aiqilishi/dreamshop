package cn.mmko.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 商品分类表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class CategoryPo {
    private Long categoryId;      // 分类ID
    private String categoryName;  // 分类名称
    private Integer categorySort; // 排序权重
    private Date createTime;      // 创建时间
} 