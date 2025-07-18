package cn.mmko.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryCreateDTO {
    private Long userId;       // 用户ID
    private String categoryName;  // 分类名称
}
