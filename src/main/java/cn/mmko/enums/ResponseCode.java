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
    UN_ERROR("500", "未知失败"),
    ILLEGAL_PARAMETER("401", "非法参数"),
    USER_NOT_EXIST("404", "用户不存在"),
    USER_EXIST("0005", "用户已存在"),
    PASSWORD_ERROR("400", "账号密码错误"),
    PRODUCT_NOT_EXIST("404", "商品不存在或下架"),
    SELLER_NAME_EXIST("0006", "商家名称已存在"),
    SELLER_PHONE_EXIST("0007", "商家手机号已存在"),
    CATEGORY_EXIST( "0008", "分类已存在"),
    PRODUCT_EXIST("0009", "商品已存在"),
    IMAGES_NOT_EXIST("404", "没有图片"),
    IMAGES_TOO_BIG("0011", "图片过大"),
    IMAGES_NAME_ERROR("0012", "图片名称错误"),
    IMAGES_TYPE_ERROR("0013", "图片格式错误"),
    FORBIDDEN("403", "禁止访问"),
    PRODUCT_NOT_ENOUGH("0010", "商品库存不足"),
    PRICE_ERROR("0011", "价格错误"),
    SELLER_NOT_EXIST("404", "商家不存在");





    private String code;
    private String info;

}
