package cn.mmko.service.customer.imp;

import cn.mmko.dao.ICustomerDao;
import cn.mmko.dto.CustomerUpdateDTO;
import cn.mmko.po.CustomerPo;
import cn.mmko.service.customer.ICustomerService;
import cn.mmko.vo.CustomerInfoVO;
import cn.mmko.vo.CustomerManageListVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerService implements ICustomerService {
    @Resource
    private ICustomerDao customerDao;
    @Override
    public void updateCustomerMessage(Long userId, CustomerUpdateDTO customerUpdateDTO) {
        customerDao.updateCustomer(CustomerPo.builder()
                .customerId(userId)
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

    @Override
    public void updateCustomerAvatar(Long userId, String avatarUrl) {
          customerDao.upsertAvatar(userId,avatarUrl);
    }

    @Override
    public Long queryCustomerIdByUserId(Long userId) {
        return customerDao.queryCustomerIdByUserId(userId);
    }

    @Override
    public CustomerPo queryCustomerById(Long userId) {
        return customerDao.queryUserByUserId(userId);
    }

    @Override
    public PageInfo<CustomerManageListVO> queryCustomerByFilter(Integer pageNum, Integer pageSize, Integer status, String keyword) {
        PageHelper.startPage(pageNum,pageSize);
        List<CustomerManageListVO> customerManageListVOS = customerDao.queryCustomerByFilter(pageNum,pageSize,status,keyword);


    }
}
