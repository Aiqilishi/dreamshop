package cn.mmko.dao;

import cn.mmko.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserDao {
    User queryUserByUserName(String userName);
    void insertUser(User user);
}
