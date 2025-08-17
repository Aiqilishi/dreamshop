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
import cn.mmko.vo.SellerManageListVO;
import cn.mmko.vo.SellerMessageVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SellerService implements ISellerService {
    @Resource
    private ISellerDao sellerDao;
    @Resource
    private IUserRoleDao userRoleDao;
    @Override
    public void insertSeller(SellerCreateDTO sellerCreateDTO,Long userId) {
        SellerPo sellerByName = sellerDao.querySellerByName(sellerCreateDTO.getSellerName());
        SellerPo sellerByPhone = sellerDao.querySellerContactPhone(sellerCreateDTO.getContactPhone());
        if(sellerByName!=null) throw new AppException(ResponseCode.SELLER_NAME_EXIST.getCode(), ResponseCode.SELLER_NAME_EXIST.getInfo());
        if(sellerByPhone!=null) throw new AppException(ResponseCode.SELLER_PHONE_EXIST.getCode(), ResponseCode.SELLER_PHONE_EXIST.getInfo());
        SellerPo sellerPo = SellerPo.builder()
                .userId(userId)
                .sellerName(sellerCreateDTO.getSellerName())
                .contactPerson(sellerCreateDTO.getContactPerson())
                .address(sellerCreateDTO.getAddress())
                .logoUrl(sellerCreateDTO.getLogoUrl())
                .contactPhone(sellerCreateDTO.getContactPhone())
                .build();
        sellerDao.insertSeller(sellerPo);
        userRoleDao.insertUserRole(UserRolePo.builder()
                        .roleId(2L)
                        .userId(userId)
                        .build());

    }

    @Override
    public void checkSellerStatus(Set<Long> sellerIds) {
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

    @Override
    public SellerPo querySellerById(Long sellerId) {
        return sellerDao.querySellerById(sellerId);
    }

    @Override
    public SellerPo querySellerByUserId(Long userId) {
        return sellerDao.querySellerByUserId(userId);
    }

    @Override
    public PageInfo<SellerManageListVO> querySellerByFilter(Integer pageNum, Integer pageSize, Integer status, String keyword) {
        PageHelper.startPage(pageNum,pageSize);
        List<SellerManageListVO> sellerManageListVOS = sellerDao.querySellerByFilter(pageNum,pageSize,status,keyword);
        return new PageInfo<>(sellerManageListVOS);
    }
    
    @Override
    public void updateSellerStatus(Long sellerId, Integer status) {
        sellerDao.updateSellerStatus(sellerId, status);
    }

    @Override
    public void checkSellerStatusByUserId(Long userId) {
        Integer status = sellerDao.checkSellerStatusByUserId(userId);
        if(status== 0){
            throw new AppException(ResponseCode.FORBIDDEN.getCode(),"商家已被禁用");
        }
    }

    @Override
    public SellerMessageVO querySellerMessage(Long sellerId) {
        SellerPo sellerPo = sellerDao.querySellerById(sellerId);
        if(sellerPo.getStatus()==0) throw new AppException(ResponseCode.FORBIDDEN.getCode(),"商家已被禁用");
        return SellerMessageVO.builder()
                .sellerId(sellerPo.getSellerId())
                .sellerName(sellerPo.getSellerName())
                .contactPhone(sellerPo.getContactPhone())
                .address(sellerPo.getAddress())
                .logoUrl(sellerPo.getLogoUrl())
                .build();
    }
}
