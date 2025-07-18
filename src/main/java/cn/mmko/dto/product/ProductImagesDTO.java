package cn.mmko.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImagesDTO {
    private Long productImageId;
    private String productImage;
    private Integer sortOrder;
}
