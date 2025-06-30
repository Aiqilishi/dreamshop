package cn.mmko.infrastructure.persistent.repository;

import cn.mmko.domain.user.model.entity.UserActionEntity;
import cn.mmko.domain.user.repository.IUserRepository;
import cn.mmko.infrastructure.persistent.dao.IUserDao;
import cn.mmko.infrastructure.persistent.po.User;
import cn.mmko.infrastructure.persistent.redis.IRedisService;
import cn.mmko.infrastructure.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
@Slf4j
@Repository
public class UserRepository implements IUserRepository {
    @Resource
    private IUserDao userDao;
    @Resource
    private IRedisService redisService;
    @Override
    public void insertUser(UserActionEntity userActionEntity) {
           userDao.insertUser(User.builder()
                   .userName(userActionEntity.getUserName())
                   .passwordHash(userActionEntity.getPasswordHash())
                   .passwordSalt(userActionEntity.getPasswordSalt())
                   .build());
    }
    @Override
    public UserActionEntity queryUserByUserName(String userName) {
        User user = userDao.queryUserByUserName(userName);
        if(user==null) return null;
        return UserActionEntity.builder()
                .userName(user.getUserName())
                .passwordHash(user.getPasswordHash())
                .passwordSalt(user.getPasswordSalt())
                .build();
    }

    @Override
    public String createUserToken(String userName) {
        String token = JwtUtils.generateJwt(userName);
        redisService.setValue("user_token:"+userName, token,60*60*24*7);
        return token;
    }


}
