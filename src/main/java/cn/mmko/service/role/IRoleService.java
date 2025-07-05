package cn.mmko.service.role;

import java.util.List;

public interface IRoleService {
    List<String> queryRoleByRoleIds(List<Long> userRoleIds);
}
