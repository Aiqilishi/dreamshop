package cn.mmko.domain.user.service;

import cn.mmko.domain.user.model.entity.UserActionEntity;
import cn.mmko.domain.user.repository.IUserRepository;
import cn.mmko.types.common.Constants;
import cn.mmko.types.enums.ResponseCode;
import cn.mmko.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserService implements IUserService{
    @Resource
    private IUserRepository userRepository;
    @Resource
    private PasswordEncryptionService passwordEncryptionService;
    @Override
    public void insertUser(String userName, String password) {
        UserActionEntity userActionEntitySearch = userRepository.queryUserByUserName(userName);
        if(userActionEntitySearch != null) throw new AppException(ResponseCode.USER_EXIST.getCode(), ResponseCode.USER_EXIST.getInfo());
        String salt = passwordEncryptionService.generateSalt();
        UserActionEntity userActionEntity = UserActionEntity.builder()
                .userName(userName)
                .passwordHash(passwordEncryptionService.hashPassword(password, salt))
                .passwordSalt(salt)
                .build();
        userRepository.insertUser(userActionEntity);
    }

    @Override
    public String checkLoginUser(String userName, String password) {
        UserActionEntity userActionEntity = userRepository.queryUserByUserName(userName);
        if(null==userActionEntity){
            throw new AppException(ResponseCode.USER_NOT_EXIST.getCode(), ResponseCode.USER_NOT_EXIST.getInfo());
        }
        if(!passwordEncryptionService.verifyPassword(password, userActionEntity.getPasswordSalt(), userActionEntity.getPasswordHash())){
            log.info("用户{}密码错误",passwordEncryptionService.hashPassword(password, userActionEntity.getPasswordSalt()));
            throw new AppException(ResponseCode.PASSWORD_ERROR.getCode(), ResponseCode.PASSWORD_ERROR.getInfo());
        }
        return userRepository.createUserToken(userName);
    }
}
