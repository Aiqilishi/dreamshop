package cn.mmko.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 购物车表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class CartPo {
    private Long cartId;      // 购物车ID
    private Long userId;      // 用户ID
    private Long productId;   // 商品ID
    private Integer quantity; // 数量
    private Date createTime;  // 创建时间
}
