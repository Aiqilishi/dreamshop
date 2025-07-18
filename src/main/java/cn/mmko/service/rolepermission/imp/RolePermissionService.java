package cn.mmko.service.rolepermission.imp;

import cn.mmko.dao.IRolePermissionDao;
import cn.mmko.service.rolepermission.IRolePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class RolePermissionService implements IRolePermissionService {
    @Resource
    private IRolePermissionDao rolePermissionDao;
    @Override
    public List<Long> queryPermissionByRoleIds(List<Long> userRoleIds) {
        return rolePermissionDao.queryPermissionByRoleIds(userRoleIds);
    }
}
