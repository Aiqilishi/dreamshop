package cn.mmko.message;

import cn.mmko.dto.OrderItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<OrderItemDTO> orderItemDTOS;
    private Long orderId;
    private String action;
    private Long timestamp;
}
