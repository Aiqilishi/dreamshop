package cn.mmko.service.rolepermission;

import java.util.List;

public interface IRolePermissionService {
    List<Long> queryPermissionByRoleIds(List<Long> userRoleIds);
}
