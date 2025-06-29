package cn.mmko.infrastructure.persistent.repository;

import cn.mmko.domain.user.model.entity.UserActionEntity;
import cn.mmko.domain.user.repository.IUserRepository;
import cn.mmko.infrastructure.persistent.dao.IUserDao;
import cn.mmko.infrastructure.persistent.po.User;
import cn.mmko.infrastructure.utils.PassWordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
@Slf4j
@Repository
public class UserRepository implements IUserRepository {
    @Resource
    private IUserDao userDao;
    @Override
    public String insertUser(UserActionEntity userActionEntity) {
        UserActionEntity userActionSearch = queryUserByUserName(userActionEntity);
        if(userActionSearch!=null){
            return "exit";
        }
        else {
            String salt = PassWordUtils.generateSalt();
           String passwordHash = PassWordUtils.hashPassword(userActionEntity.getPasswordHash(), salt);
           userDao.insertUser(User.builder()
                   .userName(userActionEntity.getUserName())
                   .passwordHash(passwordHash)
                   .passwordSalt(salt)
                   .build());
           return "finish";
        }

    }

    @Override
    public UserActionEntity queryUserByUserName(UserActionEntity userActionEntity) {
        User user = userDao.queryUserByUserName(userActionEntity.getUserName());
        if(user==null) return null;
        return UserActionEntity.builder()
                .userName(user.getUserName())
                .passwordHash(user.getPasswordHash())
                .passwordSalt(user.getPasswordSalt())
                .build();
    }

    @Override
    public boolean checkLoginUser(UserActionEntity userActionEntity) {
        User user = userDao.queryUserByUserName(userActionEntity.getUserName());
        if(user==null) return false;
        return PassWordUtils.verifyPassword(userActionEntity.getPasswordHash(),user.getPasswordSalt(),user.getPasswordHash());
    }


}
