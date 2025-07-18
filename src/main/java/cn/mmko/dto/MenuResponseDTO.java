package cn.mmko.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuResponseDTO {
    private Long permissionId;
    private String permName;
    private String permCode;
    private String permType;
    private String url;
    private Long parentId;
    private List<MenuResponseDTO> children;
}
