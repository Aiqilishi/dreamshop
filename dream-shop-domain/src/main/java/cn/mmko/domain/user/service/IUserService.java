package cn.mmko.domain.user.service;

public interface IUserService {
    void insertUser(String userName, String password);

    String checkLoginUser(String userName, String password);
}
