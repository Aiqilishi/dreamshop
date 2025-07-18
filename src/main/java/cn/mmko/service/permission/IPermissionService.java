package cn.mmko.service.permission;

import cn.mmko.dto.MenuResponseDTO;

import java.util.List;

public interface IPermissionService {
    List<String> queryPermissionByPermissionIds(List<Long> permissionsIds);

    List<MenuResponseDTO> queryUserMenu(List<String> permCodes);
}
