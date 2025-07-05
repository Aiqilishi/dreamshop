package cn.mmko.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface IRoleDao {
    List<String> queryRoleByRoleIds(List<Long> userRoleIds);
}
