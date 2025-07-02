package cn.mmko.dao;

import cn.mmko.po.UserPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserDao {
    UserPo queryUserByUserName(String userName);
    void insertUser(UserPo userPo);
}
