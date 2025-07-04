package cn.mmko.dao;

import cn.mmko.dto.SellerCreateDTO;
import cn.mmko.po.SellerPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ISellerDao {
    void insertSeller(SellerPo sellerPo);

    SellerPo querySellerByName(String sellerName);

    SellerPo querySellerBycontactPhone(String contactPhone);
}
