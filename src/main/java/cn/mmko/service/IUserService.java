package cn.mmko.service;

public interface IUserService {
    void insertUser(String userName, String password);

    String checkLoginUser(String userName, String password);
}
