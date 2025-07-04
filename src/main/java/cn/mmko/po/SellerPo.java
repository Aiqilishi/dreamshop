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
    private Long sellerId;      // 商家ID
    private Long userId;        // 关联用户ID
    private String sellerName;  // 商家名称
    private String contactPhone;// 联系电话
    private Integer status;     // 状态(1:正常,0:禁用)
    private Date createTime;    // 创建时间
} 