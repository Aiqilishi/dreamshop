package cn.mmko.infrastructure.persistent.dao;

import cn.mmko.infrastructure.persistent.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserDao {
    User queryUserByUserName(String userName);
    void insertUser(User user);
}
