package cn.mmko.service;

import cn.mmko.dto.UserMessageDTO;
import cn.mmko.po.UserPo;
import cn.mmko.dao.IUserDao;
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
        UserPo userPoSearch = userDao.queryUserByUserName(userName);
        if(userPoSearch != null) throw new AppException(ResponseCode.USER_EXIST.getCode(), ResponseCode.USER_EXIST.getInfo());
        String salt = passwordEncryptionService.generateSalt();
        UserPo userPo = UserPo.builder()
                .userName(userName)
                .passwordHash(passwordEncryptionService.hashPassword(password, salt))
                .passwordSalt(salt)
                .build();
        userDao.insertUser(userPo);
    }

    @Override
    public String checkLoginUser(String userName, String password) {
        UserPo userPo = userDao.queryUserByUserName(userName);
        if(null== userPo){
            throw new AppException(ResponseCode.USER_NOT_EXIST.getCode(), ResponseCode.USER_NOT_EXIST.getInfo());
        }
        if(!passwordEncryptionService.verifyPassword(password, userPo.getPasswordSalt(), userPo.getPasswordHash())){
            log.info("用户{}密码错误",passwordEncryptionService.hashPassword(password, userPo.getPasswordSalt()));
            throw new AppException(ResponseCode.PASSWORD_ERROR.getCode(), ResponseCode.PASSWORD_ERROR.getInfo());
        }
        String token = JwtUtils.generateJwt(userName);
        redisService.setValue( "token: "+userName, token);
        return token;
    }

    @Override
    public UserMessageDTO queryUserByUserName(String userName) {
        UserPo userPo = userDao.queryUserByUserName(userName);
        if(null== userPo){
            throw new AppException(ResponseCode.USER_NOT_EXIST.getCode(), ResponseCode.USER_NOT_EXIST.getInfo());
        }
        return UserMessageDTO.builder()
                .userName(userPo.getUserName())
                .email(userPo.getEmail())
                .phone(userPo.getPhone())
                .avatarUrl(userPo.getAvatarUrl())
                .build();
    }
}
