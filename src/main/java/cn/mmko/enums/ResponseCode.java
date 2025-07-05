package cn.mmko.enums;

import com.mysql.cj.protocol.Warning;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS("200", "成功"),
    UNAUTHORIZED("401", "用户未授权"),
    UN_ERROR("0001", "未知失败"),
    ILLEGAL_PARAMETER("401", "非法参数"),
    USER_NOT_EXIST("404", "用户不存在"),
    USER_EXIST("0005", "用户已存在"),
    PASSWORD_ERROR("400", "账号密码错误"),
    PRODUCT_NOT_EXIST("404", "商品不存在"),
    SELLER_NAME_EXIST("0006", "商家名称已存在"),
    SELLER_PHONE_EXIST("0007", "商家手机号已存在"),
    CATEGORY_EXIST( "0008", "分类已存在"),
    PRODUCT_EXIST("0009", "商品已存在")    ;

    private String code;
    private String info;

}
