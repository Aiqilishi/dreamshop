package cn.mmko.domain.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserActionEntity {
    private String userName;
    private String passwordHash;
    private String passwordSalt;
}
