package cn.mmko.service.seller;

import cn.mmko.dto.SellerCreateDTO;
import cn.mmko.po.SellerPo;
import cn.mmko.vo.SellerManageListVO;
import cn.mmko.vo.SellerMessageVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Set;

public interface ISellerService {
    void insertSeller(SellerCreateDTO sellerCreateDTO,Long userId);

    void checkSellerStatus(Set<Long> items);

    SellerPo querySellerById(Long sellerId);

    SellerPo querySellerByUserId(Long userId);

    PageInfo<SellerManageListVO> querySellerByFilter(Integer pageNum, Integer pageSize, Integer status, String keyword);
    
    void updateSellerStatus(Long sellerId, Integer status);

    void checkSellerStatusByUserId(Long userId);

    SellerMessageVO querySellerMessage(Long sellerId);
}
