package cn.mmko.service.user.imp;

import cn.mmko.dao.IRoleDao;
import cn.mmko.dao.IUserRoleDao;
import cn.mmko.dto.UserMessageDTO;
import cn.mmko.po.UserPo;
import cn.mmko.dao.IUserDao;
import cn.mmko.enums.ResponseCode;
import cn.mmko.exception.AppException;
import cn.mmko.po.UserRolePo;
import cn.mmko.redis.IRedisService;
import cn.mmko.service.role.IRoleService;
import cn.mmko.service.user.IUserService;
import cn.mmko.service.userrole.IUserRoleService;
import cn.mmko.service.utils.imp.PasswordEncryptionService;
import cn.mmko.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserService implements IUserService {
    @Resource
    private IRedisService redisService;
    @Resource
    private IUserDao userDao;
    @Resource
    private IUserRoleService userRoleService;
    @Resource
    private IRoleService roleService;
    @Resource
    private PasswordEncryptionService passwordEncryptionService;
    @Override
    @Transactional(rollbackFor = Exception.class)
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
//        if (true) {
//            throw new RuntimeException("测试事务回滚");
//        }
        userRoleService.insertUserRole(UserRolePo.builder()
                .userId(userPo.getUserId())
                .roleId(1L)
                .build());
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
        log.info("用户{}",userPo.getUserId());
        List<Long> userRoleIds = userRoleService.queryUserRoleByUserId(userPo.getUserId());
        log.info("角色{}",userRoleIds);
        List<String> roles = roleService.queryRoleByRoleIds(userRoleIds);
        String token = JwtUtils.generateJwt(userName,roles);
        //保存登录信息，角色权限保存到了redis中
        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put("token:"+userName, token);
        loginInfo.put("role:"+userName, roles.toString());
        RMap<String, String> redisServiceMap = redisService.getMap("login:" + userName);
        redisServiceMap.putAll(loginInfo);
        redisServiceMap.expire(Duration.ofHours(24));
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
                .phone(userPo.getPhone())
                .build();
    }
}
