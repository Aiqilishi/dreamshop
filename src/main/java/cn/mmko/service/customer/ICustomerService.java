package cn.mmko.service.customer;

import cn.mmko.dto.CustomerUpdateDTO;
import cn.mmko.vo.CustomerInfoVO;

public interface ICustomerService {
    void updateCustomerMessage(Long userId, CustomerUpdateDTO customerUpdateDTO);

    CustomerInfoVO queryCustomerByUserId(Long userId);
}
