package cn.mmko.domain.user.service;

import cn.mmko.domain.user.model.entity.UserActionEntity;
import cn.mmko.domain.user.repository.IUserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService implements IUserService{
    @Resource
    private IUserRepository userRepository;
    @Override
    public String insertUser(String userName, String password) {
        UserActionEntity userActionEntity = UserActionEntity.builder()
                .userName(userName)
                .passwordHash(password)
                .passwordSalt("")
                .build();
        return userRepository.insertUser(userActionEntity);
    }

    @Override
    public boolean checkLoginUser(String userName, String password) {
        UserActionEntity userActionEntity = UserActionEntity.builder()
                .userName(userName)
                .passwordHash(password)
                .passwordSalt("")
                .build();
        boolean result = userRepository.checkLoginUser(userActionEntity);
        return result;
    }
}
