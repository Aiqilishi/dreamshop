package cn.mmko.service.seller.imp;

import cn.mmko.dao.ISellerDao;
import cn.mmko.dao.IUserRoleDao;
import cn.mmko.dto.OrderItemDTO;
import cn.mmko.dto.SellerCreateDTO;
import cn.mmko.enums.ResponseCode;
import cn.mmko.exception.AppException;
import cn.mmko.po.SellerPo;
import cn.mmko.po.UserRolePo;
import cn.mmko.service.seller.ISellerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SellerService implements ISellerService {
    @Resource
    private ISellerDao sellerDao;
    @Resource
    private IUserRoleDao userRoleDao;
    @Override
    public void insertSeller(SellerCreateDTO sellerCreateDTO) {
        SellerPo sellerByName = sellerDao.querySellerByName(sellerCreateDTO.getSellerName());
        SellerPo sellerByPhone = sellerDao.querySellerContactPhone(sellerCreateDTO.getContactPhone());
        if(sellerByName!=null) throw new AppException(ResponseCode.SELLER_NAME_EXIST.getCode(), ResponseCode.SELLER_NAME_EXIST.getInfo());
        if(sellerByPhone!=null) throw new AppException(ResponseCode.SELLER_PHONE_EXIST.getCode(), ResponseCode.SELLER_PHONE_EXIST.getInfo());
        SellerPo sellerPo = SellerPo.builder()
                .userId(sellerCreateDTO.getUserId())
                .sellerName(sellerCreateDTO.getSellerName())
                .contactPhone(sellerCreateDTO.getContactPhone())
                .build();
        sellerDao.insertSeller(sellerPo);
        userRoleDao.insertUserRole(UserRolePo.builder()
                        .roleId(2L)
                        .userId(sellerCreateDTO.getUserId())
                        .build());

    }

    @Override
    public void checkSellerStatus(List<OrderItemDTO>  items) {
        Set<Long> sellerIds = items.stream()
                .map(OrderItemDTO::getSellerId)
                .collect(Collectors.toSet());

        for (Long sellerId : sellerIds) {
            Integer status = sellerDao.checkSellerStatus(sellerId);
            if (status == null) {
                throw new AppException(ResponseCode.SELLER_NOT_EXIST.getCode(),
                        "商家不存在，商家ID: " + sellerId);
            }
            if (status == 0) {
                throw new AppException(ResponseCode.FORBIDDEN.getCode(),
                        "商家已被禁用，商家ID: " + sellerId);
            }
        }
    }
}
