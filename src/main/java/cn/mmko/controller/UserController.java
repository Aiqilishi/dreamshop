package cn.mmko.controller;

import cn.mmko.dto.LoginRequestDTO;
import cn.mmko.dto.UserMessageDTO;
import cn.mmko.service.IUserService;
import cn.mmko.enums.ResponseCode;
import cn.mmko.exception.AppException;
import cn.mmko.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
   public Response<String> insertUser(@RequestBody LoginRequestDTO loginRequestDTO) {
            userService.insertUser(loginRequestDTO.getUserName(), loginRequestDTO.getPassWord());
            return Response.<String>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data("finish")
                    .build();
   }

   @RequestMapping(value = "/check",method = RequestMethod.POST)
   public Response<String> checkLoginUser(@RequestBody LoginRequestDTO loginRequestDTO){
            String token = userService.checkLoginUser(loginRequestDTO.getUserName(), loginRequestDTO.getPassWord());
            log.info("用户{}登录成功",token);
            return Response.<String>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(token)
                    .build();
   }
   @RequestMapping(value = "/message",method = RequestMethod.GET)
   public Response<UserMessageDTO> queryUserMessage(HttpServletRequest  request){
            String userName = (String) request.getAttribute("username");//从拦截器中获取
            UserMessageDTO userMessageDTO = userService.queryUserByUserName(userName);
            return Response.<UserMessageDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(userMessageDTO)
                    .build();

   }

}
