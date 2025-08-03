package cn.mmko.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderNumberVO {
      private Integer UnpaidOrderNumber;
      private Integer WaitingForDeliveryOrderNumber;
      private Integer WaitingForReceiptOrderNumber;
      private Integer CompletedOrderNumber;
      private Integer CanceledOrderNumber;
}
