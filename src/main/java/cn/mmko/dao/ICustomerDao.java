package cn.mmko.dao;

import cn.mmko.po.CustomerPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICustomerDao {

    CustomerPo queryUserByUserId(Long userId);

    void insertCustomer(Long userId);

    void updateCustomer(CustomerPo  customerPo);

    void upsertAvatar(Long userId, String avatarUrl);
}
