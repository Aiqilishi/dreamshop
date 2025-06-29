package cn.mmko.trigger.http;

import cn.mmko.domain.user.service.IUserService;
import cn.mmko.types.enums.ResponseCode;
import cn.mmko.types.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserInsertController {
    @Resource
    private IUserService userService;
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
   public Response<String> insertUser(@RequestParam String userName, @RequestParam String passWord) {
        String result =userService.insertUser(userName,passWord);
        try{
            if(result.equals("exit")) return Response.<String>builder()
                    .code(ResponseCode.USER_EXIST.getCode())
                    .info(ResponseCode.USER_EXIST.getInfo())
                    .data("user exist")
                    .build();
            else return Response.<String>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data("insert user success")
                    .build();
        }catch (Exception e){
            return Response.<String>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .data("insert user error")
                    .build();
        }
   }

   @RequestMapping(value = "/check",method = RequestMethod.GET)
   public Response<String> checkLoginUser(@RequestParam String userName, @RequestParam String passWord){
        boolean result = userService.checkLoginUser(userName,passWord);
        if(!result) return Response. <String>builder()
                .code(ResponseCode.PASSWORD_ERROR.getCode())
                .info(ResponseCode.PASSWORD_ERROR.getInfo())
                .data("password error")
                .build() ;
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data("login success")
                .build();
   }

}
