package cn.mmko.service.seller;

import cn.mmko.dto.OrderItemDTO;
import cn.mmko.dto.SellerCreateDTO;
import cn.mmko.po.SellerPo;

import java.util.List;

public interface ISellerService {
    void insertSeller(SellerCreateDTO sellerCreateDTO,Long userId);

    void checkSellerStatus(List<OrderItemDTO> items);

    SellerPo querySellerById(Long sellerId);
}
