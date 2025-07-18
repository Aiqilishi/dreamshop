package cn.mmko.dao;

import cn.mmko.po.PermissionPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IPermissionDao {

    List<String> queryPermissionByPermissionIds(List<Long> permissionsIds);

    List<PermissionPo> queryPermissionBycode(List<String> permCodes);
}
