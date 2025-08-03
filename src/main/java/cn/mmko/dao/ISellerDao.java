package cn.mmko.dao;

import cn.mmko.po.SellerPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ISellerDao {
    void insertSeller(SellerPo sellerPo);

    SellerPo querySellerByName(String sellerName);

    SellerPo querySellerContactPhone (String contactPhone);

   Integer checkSellerStatus(Long sellerId);

    SellerPo querySellerById(Long sellerId);
}
