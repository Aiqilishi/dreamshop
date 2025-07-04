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
    private String userId;
    private String userName;
    private String passwordHash;
    private String passwordSalt;
    private String email;
    private String phone;
    private String avatarUrl;
    private String userStatus;
    private String registerTime;
    private String lastLoginTime;
    private String loginIp;
    private Integer userType;      // 用户类型(0:普通用户,1:商家,2:管理员)
    private Integer isDeleted;     // 是否删除(0:否,1:是)
}
