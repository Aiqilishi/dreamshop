package cn.mmko.service.permission.imp;

import cn.mmko.dao.IPermissionDao;
import cn.mmko.dto.MenuResponseDTO;
import cn.mmko.po.PermissionPo;
import cn.mmko.service.permission.IPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class PermissionService implements IPermissionService {
    @Resource
    IPermissionDao permissionDao;
    @Override
    public List<String> queryPermissionByPermissionIds(List<Long> permissionsIds) {
        return permissionDao.queryPermissionByPermissionIds(permissionsIds);
    }

    @Override
    public List<MenuResponseDTO> queryUserMenu(List<String> permCodes) {
         List<PermissionPo> permissionPos = permissionDao.queryPermissionBycode(permCodes);
         List<PermissionPo> menus = permissionPos.stream()
                 .filter(permissionPo -> permissionPo.getPermType()
                         .equals("menu")).collect(Collectors.toList());
        return buildMenuTree(menus,0L);
    }
    private List<MenuResponseDTO> buildMenuTree(List<PermissionPo> menus, Long parentId){
        List<MenuResponseDTO> menuResponseDTOS = new ArrayList<>();
        for(PermissionPo menu : menus){
            if(menu.getParentId().equals(parentId)){
                MenuResponseDTO menuResponseDTO = MenuResponseDTO.builder()
                        .permissionId(menu.getPermissionId())
                        .permName(menu.getPermName())
                        .permCode(menu.getPermCode())
                        .permType(menu.getPermType())
                        .url(menu.getUrl())
                        .parentId(menu.getParentId())
                        .children(buildMenuTree(menus,menu.getPermissionId()))
                        .build();
                menuResponseDTOS.add(menuResponseDTO);
            }
        }
     return menuResponseDTOS;
    }
}
