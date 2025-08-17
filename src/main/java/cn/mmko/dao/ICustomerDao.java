package cn.mmko.dao;

import cn.mmko.po.CustomerPo;
import cn.mmko.vo.CustomerManageListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ICustomerDao {

    CustomerPo queryUserByUserId(Long userId);

    void insertCustomer(Long userId);

    void updateCustomer(CustomerPo  customerPo);

    void upsertAvatar(Long userId, String avatarUrl);

    Long queryCustomerIdByUserId(Long userId);

    List<CustomerManageListVO> queryCustomerByFilter(Integer pageNum, Integer pageSize, Integer status, String keyword);

}
