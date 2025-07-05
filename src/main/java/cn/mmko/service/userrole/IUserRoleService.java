package cn.mmko.service.userrole;

import cn.mmko.po.UserRolePo;

import java.util.List;

public interface IUserRoleService {
    List<Long> queryUserRoleByUserId(Long userId);

    void insertUserRole(UserRolePo build);
}
