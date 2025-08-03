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
    private String sellerName;  // 商家名称
    private String contactPhone;// 联系电话
    private String contactPerson;// 联系人
    private String address;
    private String logoUrl;
}
