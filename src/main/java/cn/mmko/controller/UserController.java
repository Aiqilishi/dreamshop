package cn.mmko.controller;

import cn.mmko.dto.*;
import cn.mmko.service.permission.IPermissionService;
import cn.mmko.service.user.IUserService;
import cn.mmko.enums.ResponseCode;
import cn.mmko.response.Response;
import cn.mmko.vo.CustomerInfoVO;
import cn.mmko.vo.UserLoginResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;
    @Resource
    private IPermissionService permissionService;
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
   public Response<String> insertUser(@RequestBody UserCreateDTO userCreateDTO) {
            userService.insertUser(userCreateDTO);
            return Response.<String>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data("finish")
                    .build();
   }
   @RequestMapping(value = "/check",method = RequestMethod.POST)
   public Response<UserLoginResponseVO> checkLoginUser(@RequestParam String userName, @RequestParam String passWord){
            UserLoginResponseVO userLoginResponseVO = userService.checkLoginUser(userName, passWord);
            log.info("用户{}登录成功",userLoginResponseVO.getToken());
            return Response.<UserLoginResponseVO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(userLoginResponseVO)
                    .build();
   }


    public Response<List<MenuResponseDTO>> queryUserMenu(HttpServletRequest request){
        List<String> permCodes = (List<String>) request.getAttribute("permission");
        List<MenuResponseDTO> menuResponseDTOS = permissionService.queryUserMenu(permCodes);
        return Response.<List<MenuResponseDTO>>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(menuResponseDTOS)
                .build();
    }

}
