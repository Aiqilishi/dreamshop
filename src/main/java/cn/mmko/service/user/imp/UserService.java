package cn.mmko.service.user.imp;

import cn.mmko.dao.ICustomerDao;
import cn.mmko.dto.CustomerUpdateDTO;
import cn.mmko.dto.UserCreateDTO;
import cn.mmko.po.CustomerPo;
import cn.mmko.po.UserPo;
import cn.mmko.dao.IUserDao;
import cn.mmko.enums.ResponseCode;
import cn.mmko.exception.AppException;
import cn.mmko.po.UserRolePo;
import cn.mmko.redis.IRedisService;
import cn.mmko.service.permission.IPermissionService;
import cn.mmko.service.role.IRoleService;
import cn.mmko.service.rolepermission.IRolePermissionService;
import cn.mmko.service.user.IUserService;
import cn.mmko.service.userrole.IUserRoleService;
import cn.mmko.service.utils.imp.PasswordEncryptionService;
import cn.mmko.utils.JwtUtils;
import cn.mmko.vo.CustomerInfoVO;
import cn.mmko.vo.UserLoginResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserService implements IUserService {
    @Resource
    private IRedisService redisService;
    @Resource
    private IUserDao userDao;
    @Resource
    private ICustomerDao customerDao;
    @Resource
    private IUserRoleService userRoleService;
    @Resource
    private IRoleService roleService;
    @Resource
    private IPermissionService permissionService;
    @Resource
    private IRolePermissionService rolePermissionService;
    @Resource
    private PasswordEncryptionService passwordEncryptionService;

    /**
     *   注册
     * @param userCreateDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUser(UserCreateDTO userCreateDTO) {
        UserPo userPoSearch = userDao.queryUserByUserName(userCreateDTO.getUserName());
        if(userPoSearch != null) throw new AppException(ResponseCode.USER_EXIST.getCode(), ResponseCode.USER_EXIST.getInfo());
        String salt = passwordEncryptionService.generateSalt();
        UserPo userPo = UserPo.builder()
                .userName(userCreateDTO.getUserName())
                .passwordHash(passwordEncryptionService.hashPassword(userCreateDTO.getPassWord(), salt))
                .passwordSalt(salt)
                .phone(userCreateDTO.getPhone())
                .build();
        userDao.insertUser(userPo);
        customerDao.insertCustomer(userPo.getUserId());//先添加顾客信息表，以防后续查出null，无法修改信息
//        if (true) {
//            throw new RuntimeException("测试事务回滚");
//        }
        userRoleService.insertUserRole(UserRolePo.builder()
                .userId(userPo.getUserId())
                .roleId(1L)
                .build());
    }

    /***
     *  登录
     * @param userName
     * @param password
     * @return
     */
    @Override
    public UserLoginResponseVO checkLoginUser(String userName, String password) {
        UserPo userPo = userDao.queryUserByUserName(userName);
        if(null== userPo){
            throw new AppException(ResponseCode.USER_NOT_EXIST.getCode(), ResponseCode.USER_NOT_EXIST.getInfo());
        }
        if(!passwordEncryptionService.verifyPassword(password, userPo.getPasswordSalt(), userPo.getPasswordHash())){
            log.info("用户{}密码错误",passwordEncryptionService.hashPassword(password, userPo.getPasswordSalt()));
            throw new AppException(ResponseCode.PASSWORD_ERROR.getCode(), ResponseCode.PASSWORD_ERROR.getInfo());
        }
        Long userId = userPo.getUserId();
        log.info("用户{}",userPo.getUserId());
        //获取用户角色id
        List<Long> userRoleIds = userRoleService.queryUserRoleByUserId(userPo.getUserId());
        log.info("角色{}",userRoleIds);
        List<String> roles = roleService.queryRoleByRoleIds(userRoleIds);
        List<Long> permissionsIds = rolePermissionService.queryPermissionByRoleIds(userRoleIds);
        List<String> permissions = permissionService.queryPermissionByPermissionIds(permissionsIds);
        String token = JwtUtils.generateJwt(userName,roles,permissions,userId);
        //保存登录信息，角色权限保存到了redis中
        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put("token:"+userName, token);
        loginInfo.put("role:"+userName, roles.toString());
        loginInfo.put("permission:"+userName, permissions.toString());
        RMap<String, String> redisServiceMap = redisService.getMap("login:" + userName);
        redisServiceMap.putAll(loginInfo);
        redisServiceMap.expire(Duration.ofHours(24));
        return UserLoginResponseVO.builder()
                .token( token)
                .roles(roles)
                .build();
    }



}
