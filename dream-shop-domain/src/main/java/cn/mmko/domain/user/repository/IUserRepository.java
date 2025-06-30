package cn.mmko.domain.user.repository;

import cn.mmko.domain.user.model.entity.UserActionEntity;

public interface IUserRepository {
   void insertUser(UserActionEntity userActionEntity);
    UserActionEntity queryUserByUserName(String userName);

    String createUserToken(String userName);
}
