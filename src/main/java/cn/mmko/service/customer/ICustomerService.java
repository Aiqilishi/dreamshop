package cn.mmko.service.customer;

import cn.mmko.dto.CustomerUpdateDTO;
import cn.mmko.vo.CustomerInfoVO;

public interface ICustomerService {
    void updateCustomerMessage(Long userId, CustomerUpdateDTO customerUpdateDTO);

    CustomerInfoVO queryCustomerByUserId(Long userId);

    void updateCustomerAvatar(Long userId, String avatarUrl);


    Long queryCustomerIdByUserId(Long userId);
}
