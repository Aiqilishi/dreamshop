package cn.mmko.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderTimeoutMessage {
    private Long orderId;
    private Long createTime;
    private Integer timeoutMinutes;
}