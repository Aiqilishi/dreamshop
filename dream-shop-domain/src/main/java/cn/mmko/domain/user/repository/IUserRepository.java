package cn.mmko.domain.user.repository;

import cn.mmko.domain.user.model.entity.UserActionEntity;

public interface IUserRepository {
   String insertUser(UserActionEntity userActionEntity);
    UserActionEntity queryUserByUserName(UserActionEntity userActionEntity);

    boolean checkLoginUser(UserActionEntity userActionEntity);
}
