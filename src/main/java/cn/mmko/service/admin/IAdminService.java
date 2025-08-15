package cn.mmko.service.admin;

import cn.mmko.vo.CustomerManageListVO;
import cn.mmko.vo.OrderBackgroundListVO;
import cn.mmko.vo.ProductManageListVO;
import com.alipay.api.domain.OpenApiSkillGroupChannelInfo;
import com.github.pagehelper.PageInfo;

public interface IAdminService {
    Long queryAdminByUserId(Long userId);

    PageInfo<ProductManageListVO> queryAllProduct(Integer pageNum, Integer pageSize);

    PageInfo<OrderBackgroundListVO> queryAllOrder(Integer pageNum, Integer pageSize, Integer status, Integer itemStatus, String keyword);

    PageInfo<CustomerManageListVO> queryCustomerByFilter(Integer pageNum, Integer pageSize, Integer status, String keyword);
}
