package cn.mmko.service.userrole.imp;

import cn.mmko.dao.IUserRoleDao;
import cn.mmko.po.UserRolePo;
import cn.mmko.service.userrole.IUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
public class UserRoleService implements IUserRoleService {
    @Resource
    private IUserRoleDao userRoleDao;

    @Override
    public List<Long> queryUserRoleByUserId(Long userId) {
        return userRoleDao.queryUserRoleByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUserRole(UserRolePo build) {
        userRoleDao.insertUserRole(build);
    }
}
