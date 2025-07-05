package cn.mmko.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class RolePo {
    private Long roleId;         // 角色ID
    private String roleName;     // 角色名称
    private String roleCode;     // 角色编码
    private String description;  // 角色描述
    private Date createTime;     // 创建时间
} 