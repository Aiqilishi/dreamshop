package cn.mmko.dao;

import cn.mmko.po.UserPo;
import cn.mmko.po.UserRolePo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserDao {
    UserPo queryUserByUserName(String userName);
    void insertUser(UserPo userPo);

    Integer checkUserStatus(Long userId);

    void updateCustomerStatus(Long userId, Integer status);
}
