package cn.mmko.service.user;

import cn.mmko.dto.CustomerUpdateDTO;
import cn.mmko.dto.UserCreateDTO;
import cn.mmko.vo.CustomerInfoVO;
import cn.mmko.vo.UserLoginResponseVO;

public interface IUserService {
    void insertUser(UserCreateDTO userCreateDTO);

    UserLoginResponseVO checkLoginUser(String userName, String password);


    void checkUserStatus(Long userId);

    UserLoginResponseVO checkLoginBackgroundUser(String userName, String passWord);

    void updateCustomerStatus(Long customerId, Integer status);
}
