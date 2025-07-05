package cn.mmko.service.role.imp;

import cn.mmko.dao.IRoleDao;
import cn.mmko.service.role.IRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class RoleService implements IRoleService {
    @Resource
    private IRoleDao roleDao;

    @Override
    public List<String> queryRoleByRoleIds(List<Long> userRoleIds) {
        return roleDao.queryRoleByRoleIds(userRoleIds);
    }
}
