package cn.mmko.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerMessageVO {
    private Long sellerId;         // 商家ID
    private String sellerName;     // 店铺名称
    private String contactPhone;   // 联系电话
    private String address;        // 店铺地址
    private String logoUrl;        // 店铺Logo
}
