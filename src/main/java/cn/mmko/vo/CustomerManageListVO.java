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
public class CustomerManageListVO {
    private Long customerId;      // 主键
    private Long userId;          // 关联用户ID
    private String userName;
    private String nickname;      // 昵称
    private Integer gender;       // 性别(0:未知,1:男,2:女)
    private Date birthday;        // 生日
    private String address;       // 默认收货地址
    private String avatarUrl;     // 头像URL
    private Date createTime;      // 创建时间
    private Date updateTime;      // 更新时间
    private Integer status;
    private Date lastLoginTime;
}
