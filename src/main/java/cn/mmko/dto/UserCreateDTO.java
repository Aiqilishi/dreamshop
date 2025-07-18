package cn.mmko.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateDTO {
    private String userName;       // 用户名
    private String passWord;       //输入密码
    private String phone;          // 手机号
}
