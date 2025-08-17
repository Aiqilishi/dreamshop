package cn.mmko.service.admin.imp;

import cn.mmko.dao.IAdminDao;
import cn.mmko.dao.IProductDao;
import cn.mmko.service.admin.IAdminService;
import cn.mmko.service.customer.ICustomerService;
import cn.mmko.service.order.IOrderService;
import cn.mmko.service.product.IProductService;
import cn.mmko.service.seller.ISellerService;
import cn.mmko.service.user.IUserService;
import cn.mmko.vo.CustomerManageListVO;
import cn.mmko.vo.OrderBackgroundListVO;
import cn.mmko.vo.ProductManageListVO;
import cn.mmko.vo.SellerManageListVO;
import com.alipay.api.domain.OpenApiSkillGroupChannelInfo;
import com.github.pagehelper.PageInfo;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminService implements IAdminService {

    @Resource
    private IAdminDao adminDao;
    @Resource
    private IProductService productService;
    @Resource
    @Lazy
    private IOrderService orderService;
    @Resource
    private ICustomerService customerService;
    @Resource
    private ISellerService sellerService;
    @Resource
    @Lazy
    private IUserService userService;
    @Override
    public Long queryAdminByUserId(Long userId) {
        return adminDao.queryAdminByUserId(userId);
    }

    @Override
    public PageInfo<ProductManageListVO> queryAllProduct(Integer pageNum, Integer pageSize) {
        return productService.queryProductBySellerId(pageNum, pageSize, null);
    }

    @Override
    public PageInfo<OrderBackgroundListVO> queryAllOrder(Integer pageNum, Integer pageSize, Integer status, Integer itemStatus, String keyword) {
        return orderService.queryOrderBackgroundList(pageNum, pageSize, null, status, itemStatus, keyword);
    }

    @Override
    public PageInfo<CustomerManageListVO> queryCustomerByFilter(Integer pageNum, Integer pageSize, Integer status, String keyword) {
        return  customerService.queryCustomerByFilter(pageNum, pageSize, status, keyword);
    }

    @Override
    public PageInfo<SellerManageListVO> querySellerByFilter(Integer pageNum, Integer pageSize, Integer status, String keyword) {
        return sellerService.querySellerByFilter(pageNum, pageSize, status, keyword);
    }
    
    @Override
    public void updateCustomerStatus(Long customerId, Integer status) {
        userService.updateCustomerStatus(customerId, status);
    }
    
    @Override
    public void updateSellerStatus(Long sellerId, Integer status) {
        sellerService.updateSellerStatus(sellerId, status);
    }
}
