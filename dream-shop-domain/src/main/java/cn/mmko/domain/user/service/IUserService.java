package cn.mmko.domain.user.service;

public interface IUserService {
    String insertUser(String userName, String password);

    boolean checkLoginUser(String userName, String password);
}
