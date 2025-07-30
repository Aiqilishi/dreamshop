package cn.mmko.service.seller;

import cn.mmko.dto.OrderItemDTO;
import cn.mmko.dto.SellerCreateDTO;

import java.util.List;

public interface ISellerService {
    void insertSeller(SellerCreateDTO sellerCreateDTO);

    void checkSellerStatus(List<OrderItemDTO> items);
}
