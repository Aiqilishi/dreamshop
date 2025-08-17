package cn.mmko.controller;

import cn.mmko.enums.ResponseCode;
import cn.mmko.response.Response;
import cn.mmko.service.admin.IAdminService;
import cn.mmko.service.admin.imp.AdminService;
import cn.mmko.vo.CustomerManageListVO;
import cn.mmko.vo.OrderBackgroundListVO;
import cn.mmko.vo.ProductManageListVO;
import cn.mmko.vo.SellerManageListVO;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private IAdminService adminService;
   @RequestMapping(value = "/queryAllProduct",method = RequestMethod.GET)
    public Response<PageInfo<ProductManageListVO>> queryAllProduct(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize){
        PageInfo<ProductManageListVO> pageInfo = adminService.queryAllProduct(pageNum, pageSize);
        return Response. <PageInfo<ProductManageListVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(pageInfo)
                .build();
    }
   @RequestMapping(value = "/queryAllOrder",method = RequestMethod.GET)
    public Response<PageInfo<OrderBackgroundListVO>> queryAllOrder(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer itemStatus,
            @RequestParam(required = false) String keyword){
        PageInfo<OrderBackgroundListVO> pageInfo = adminService.queryAllOrder(pageNum, pageSize, status, itemStatus, keyword);
        return Response. <PageInfo<OrderBackgroundListVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(pageInfo)
                .build();
    }
@RequestMapping(value = "/queryCustomerByFilter",method = RequestMethod.GET)
    public Response<PageInfo<CustomerManageListVO>>  queryCustomerByFilter(
            @RequestParam (defaultValue = "1") Integer pageNum,
            @RequestParam (defaultValue = "5") Integer pageSize,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String keyword){
       PageInfo<CustomerManageListVO> pageInfo = adminService.queryCustomerByFilter(pageNum, pageSize, status, keyword);
       return Response. <PageInfo<CustomerManageListVO>>builder()
               .code(ResponseCode.SUCCESS.getCode())
               .info(ResponseCode.SUCCESS.getInfo())
               .data(pageInfo)
               .build();
    }
@RequestMapping(value = "/querySellerByFilter",method = RequestMethod.GET)
    public Response<PageInfo<SellerManageListVO>> querySellerByFilter(
            @RequestParam (defaultValue = "1") Integer pageNum,
            @RequestParam (defaultValue = "5") Integer pageSize,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String keyword){
        PageInfo<SellerManageListVO> pageInfo = adminService.querySellerByFilter(pageNum, pageSize, status, keyword);
        return Response. <PageInfo<SellerManageListVO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(pageInfo)
                .build();
            }
    
    @RequestMapping(value = "/updateCustomerStatus", method = RequestMethod.POST)
    public Response<String> updateCustomerStatus(
            @RequestParam Long userId,
            @RequestParam Integer status) {
            adminService.updateCustomerStatus(userId, status);
            return Response.<String>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data("状态更新成功")
                    .build();
    }
    
    @RequestMapping(value = "/updateSellerStatus", method = RequestMethod.POST)
    public Response<String> updateSellerStatus(
            @RequestParam Long sellerId,
            @RequestParam Integer status) {
            adminService.updateSellerStatus(sellerId, status);
            return Response.<String>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data("状态更新成功")
                    .build();
    }
}
