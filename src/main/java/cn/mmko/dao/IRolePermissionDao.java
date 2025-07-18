package cn.mmko.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface IRolePermissionDao {
    List<Long> queryPermissionByRoleIds(List<Long> userRoleIds);
}
