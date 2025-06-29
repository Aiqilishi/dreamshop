package cn.mmko.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS("200", "成功"),
    UN_ERROR("0001", "未知失败"),
    ILLEGAL_PARAMETER("0002", "非法参数"),
    USER_NOT_EXIST("0003", "用户不存在"),
    USER_EXIST("0005", "用户已存在"),
    PASSWORD_ERROR("0004", "账号密码错误"),
    ;

    private String code;
    private String info;

}
