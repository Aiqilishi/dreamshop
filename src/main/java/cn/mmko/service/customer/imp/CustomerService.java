package cn.mmko.service.customer.imp;

import cn.mmko.dao.ICustomerDao;
import cn.mmko.dto.CustomerUpdateDTO;
import cn.mmko.po.CustomerPo;
import cn.mmko.service.customer.ICustomerService;
import cn.mmko.vo.CustomerInfoVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CustomerService implements ICustomerService {
    @Resource
    private ICustomerDao customerDao;
    @Override
    public void updateCustomerMessage(Long userId, CustomerUpdateDTO customerUpdateDTO) {
        customerDao.updateCustomer(CustomerPo.builder()
                .userId(userId)
                .nickname(customerUpdateDTO.getNickname())
                .gender(customerUpdateDTO.getGender())
                .birthday(customerUpdateDTO.getBirthday())
                .address(customerUpdateDTO.getAddress())
                .avatarUrl(customerUpdateDTO.getAvatarUrl())
                .build());

    }

    @Override
    public CustomerInfoVO queryCustomerByUserId(Long userId) {
        CustomerPo customerPo = customerDao.queryUserByUserId(userId);
        return CustomerInfoVO.builder()
                .nickname(customerPo.getNickname())
                .gender(customerPo.getGender())
                .birthday(customerPo.getBirthday())
                .address(customerPo.getAddress())
                .avatarUrl(customerPo.getAvatarUrl())
                .build();
    }
}
