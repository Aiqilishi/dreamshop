package cn.mmko.trigger.http;

import cn.mmko.domain.user.model.dto.LoginRequestDTO;
import cn.mmko.domain.user.model.entity.UserActionEntity;
import cn.mmko.domain.user.service.IUserService;
import cn.mmko.domain.user.service.UserService;
import cn.mmko.types.enums.ResponseCode;
import cn.mmko.types.exception.AppException;
import cn.mmko.types.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserInsertController {
    @Resource
    private IUserService userService;
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
   public Response<String> insertUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            userService.insertUser(loginRequestDTO.getUserName(), loginRequestDTO.getPassWord());
            return Response.<String>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data("finish")
                    .build();
        }
        catch (AppException e){
            return Response.<String>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .data("用户已存在")
                    .build();
        }
        catch (Exception e){
            return Response.<String>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .data("未知错误")
                    .build();
        }
   }

   @RequestMapping(value = "/check",method = RequestMethod.POST)
   public Response<String> checkLoginUser(@RequestBody LoginRequestDTO loginRequestDTO){
        try{
            String token = userService.checkLoginUser(loginRequestDTO.getUserName(), loginRequestDTO.getPassWord());
            log.info("用户{}登录成功",token);
            return Response.<String>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(token)
                    .build();
        }
        catch (AppException e){
            return Response.<String>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .data("用户不存在")
                    .build();
        }
        catch (Exception e){
            return Response.<String>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .data("未知错误")
                    .build();
        }
   }

}
