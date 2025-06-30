package cn.mmko.domain.user.model.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String userName;
    private String passWord;
}
