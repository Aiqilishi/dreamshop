package cn.mmko.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerCreateDTO {
    private Long userId;        // 关联用户ID
    private String sellerName;  // 商家名称
    private String contactPhone;// 联系电话
}
