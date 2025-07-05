package cn.mmko.dao;

import cn.mmko.po.UserRolePo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface IUserRoleDao {
    List<Long> queryUserRoleByUserId(Long userId);

    void insertUserRole(UserRolePo build);
}
