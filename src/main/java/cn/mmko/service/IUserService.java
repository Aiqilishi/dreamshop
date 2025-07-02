package cn.mmko.service;

import cn.mmko.dto.UserMessageDTO;
import cn.mmko.po.UserPo;

public interface IUserService {
    void insertUser(String userName, String password);

    String checkLoginUser(String userName, String password);

    UserMessageDTO queryUserByUserName(String userName);


}
