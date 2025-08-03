package cn.mmko.controller;

import cn.mmko.config.FileUploadConfig;
import cn.mmko.dto.CustomerUpdateDTO;
import cn.mmko.dto.MenuResponseDTO;
import cn.mmko.enums.ResponseCode;
import cn.mmko.response.Response;
import cn.mmko.service.customer.ICustomerService;
import cn.mmko.service.upload.IFileUploadService;
import cn.mmko.vo.CustomerInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Resource
    private ICustomerService customerService;
    @Resource
    private IFileUploadService fileUploadService;
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Response<String> updateUserMessage(HttpServletRequest request, @RequestBody CustomerUpdateDTO customerUpdateDTO){
        Long userId = (Long) request.getAttribute("userId");
       customerService.updateCustomerMessage(userId, customerUpdateDTO);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data("finish")
                .build();
    }

    /**
     *  查询用户信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/message",method = RequestMethod.GET)
    public Response<CustomerInfoVO> queryUserMessage(HttpServletRequest  request){
        Long userId =  (Long) request.getAttribute("userId");//从拦截器中获取
        CustomerInfoVO customerInfoVO = customerService.queryCustomerByUserId(userId);
        return Response.<CustomerInfoVO>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(customerInfoVO)
                .build();
    }
@RequestMapping(value = "/updateAvatar",method = RequestMethod.POST)
    public Response<String> updateCustomerAvatar(HttpServletRequest request, @RequestParam ("file") MultipartFile file) throws Exception {
        Long userId =  (Long) request.getAttribute("userId");//从拦截器中获取
       String avatarUrl = fileUploadService.uploadAvatar(file, userId);
         customerService.updateCustomerAvatar(userId, avatarUrl);
         return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(avatarUrl)
                .build();
    }
}
