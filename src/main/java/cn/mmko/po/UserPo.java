package cn.mmko.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class UserPo {
    private Long userId;           // 用户ID
    private String userName;       // 用户名
    private String passwordHash;   // 加密后的密码
    private String passwordSalt;   // 密码盐值
    private String phone;          // 手机号
    private Integer userStatus;    // 用户状态(1:正常,0:禁用,-1:删除)
    private java.util.Date registerTime;    // 注册时间
    private java.util.Date lastLoginTime;   // 最后登录时间
    private String loginIp;        // 最后登录IP
    private String address;
    private String userUrl;
}
