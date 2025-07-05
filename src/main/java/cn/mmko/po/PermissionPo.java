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
public class PermissionPo {
    private Long permissionId;   // 权限ID
    private String permName;     // 权限名称
    private String permCode;     // 权限标识
    private String permType;     // 权限类型(菜单/按钮/接口)
    private String url;          // 关联接口或页面路径
    private Long parentId;       // 父权限ID
    private String description;  // 权限描述
} 