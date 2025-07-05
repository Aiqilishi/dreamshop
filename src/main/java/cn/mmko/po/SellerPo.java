package cn.mmko.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 商家表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class SellerPo {
    private Long sellerId;         // 商家ID
    private Long userId;           // 关联用户ID
    private String sellerName;     // 店铺名称
    private String contactPhone;   // 联系电话
    private String contactPerson;  // 联系人
    private String businessLicense;// 营业执照号/图片
    private String address;        // 店铺地址
    private String logoUrl;        // 店铺Logo
    private Integer status;        // 状态(1:正常,0:禁用)
    private Date createTime;       // 创建时间
    private Date updateTime;       // 更新时间
    private String remark;         // 备注
} 