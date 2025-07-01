package cn.mmko.service;

import cn.mmko.entity.User;
import cn.mmko.dao.IUserDao;
import cn.mmko.common.Constants;
import cn.mmko.enums.ResponseCode;
import cn.mmko.exception.AppException;
import cn.mmko.redis.IRedisService;
import cn.mmko.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserService implements IUserService{
    @Resource
    private IRedisService redisService;
    @Resource
    private IUserDao userDao;
    @Resource
    private PasswordEncryptionService passwordEncryptionService;
    @Override
    public void insertUser(String userName, String password) {
        User userSearch = userDao.queryUserByUserName(userName);
        if(userSearch != null) throw new AppException(ResponseCode.USER_EXIST.getCode(), ResponseCode.USER_EXIST.getInfo());
        String salt = passwordEncryptionService.generateSalt();
        User user = User.builder()
                .userName(userName)
                .passwordHash(passwordEncryptionService.hashPassword(password, salt))
                .passwordSalt(salt)
                .build();
        userDao.insertUser(user);
    }

    @Override
    public String checkLoginUser(String userName, String password) {
        User user = userDao.queryUserByUserName(userName);
        if(null==user){
            throw new AppException(ResponseCode.USER_NOT_EXIST.getCode(), ResponseCode.USER_NOT_EXIST.getInfo());
        }
        if(!passwordEncryptionService.verifyPassword(password, user.getPasswordSalt(), user.getPasswordHash())){
            log.info("用户{}密码错误",passwordEncryptionService.hashPassword(password, user.getPasswordSalt()));
            throw new AppException(ResponseCode.PASSWORD_ERROR.getCode(), ResponseCode.PASSWORD_ERROR.getInfo());
        }
        // TODO: 实现token生成逻辑
        String token = JwtUtils.generateJwt(userName);
        redisService.setValue( "token: "+userName, token);
        return token;
    }
}
