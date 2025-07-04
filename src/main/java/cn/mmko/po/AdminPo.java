package cn.mmko.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 管理员表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class AdminPo {
    private Long adminId;      // 管理员ID
    private String username;   // 管理员账号
    private String passwordHash; // 密码
    private String email;      // 邮箱
    private Integer status;    // 状态(1:正常,0:禁用)
    private Date createTime;   // 创建时间
} 