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
public class RolePermissionPo {
    private Long id;             // 主键
    private Long roleId;         // 角色ID
    private Long permissionId;   // 权限ID
}
