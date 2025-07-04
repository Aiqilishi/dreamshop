package cn.mmko.service.seller.imp;

import cn.mmko.dao.ISellerDao;
import cn.mmko.dto.SellerCreateDTO;
import cn.mmko.enums.ResponseCode;
import cn.mmko.exception.AppException;
import cn.mmko.po.SellerPo;
import cn.mmko.service.seller.ISellerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SellerService implements ISellerService {
    @Resource
    private ISellerDao sellerDao;
    @Override
    public void insertSeller(SellerCreateDTO sellerCreateDTO) {
        SellerPo sellerByName = sellerDao.querySellerByName(sellerCreateDTO.getSellerName());
        SellerPo sellerByPhone = sellerDao.querySellerBycontactPhone(sellerCreateDTO.getContactPhone());
        if(sellerByName!=null) throw new AppException(ResponseCode.SELLER_NAME_EXIST.getCode(), ResponseCode.SELLER_NAME_EXIST.getInfo());
        if(sellerByPhone!=null) throw new AppException(ResponseCode.SELLER_PHONE_EXIST.getCode(), ResponseCode.SELLER_PHONE_EXIST.getInfo());
        SellerPo sellerPo = SellerPo.builder()
                .userId(sellerCreateDTO.getUserId())
                .sellerName(sellerCreateDTO.getSellerName())
                .contactPhone(sellerCreateDTO.getContactPhone())
                .build();
        sellerDao.insertSeller(sellerPo);
    }
}
