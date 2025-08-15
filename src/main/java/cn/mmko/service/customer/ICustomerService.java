package cn.mmko.service.customer;

import cn.mmko.dto.CustomerUpdateDTO;
import cn.mmko.po.CustomerPo;
import cn.mmko.vo.CustomerInfoVO;
import cn.mmko.vo.CustomerManageListVO;
import com.github.pagehelper.PageInfo;

public interface ICustomerService {
    void updateCustomerMessage(Long userId, CustomerUpdateDTO customerUpdateDTO);

    CustomerInfoVO queryCustomerByUserId(Long userId);

    void updateCustomerAvatar(Long userId, String avatarUrl);


    Long queryCustomerIdByUserId(Long userId);

    CustomerPo queryCustomerById(Long userId);

    PageInfo<CustomerManageListVO> queryCustomerByFilter(Integer pageNum, Integer pageSize, Integer status, String keyword);
}
