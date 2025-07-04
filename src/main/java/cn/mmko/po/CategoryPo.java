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
    private Long parentId;         // 父分类ID，0为一级分类
    private Long sellerId;         // 商家ID，0为平台分类
} 