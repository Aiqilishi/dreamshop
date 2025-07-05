package cn.mmko.service.seller.imp;

import cn.mmko.dao.ISellerDao;
import cn.mmko.dao.IUserRoleDao;
import cn.mmko.dto.SellerCreateDTO;
import cn.mmko.enums.ResponseCode;
import cn.mmko.exception.AppException;
import cn.mmko.po.SellerPo;
import cn.mmko.po.UserRolePo;
import cn.mmko.service.seller.ISellerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
