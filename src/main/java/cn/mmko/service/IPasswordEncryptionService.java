package cn.mmko.service;

public interface IPasswordEncryptionService {
    String generateSalt();
    String hashPassword(String password, String salt);
    boolean verifyPassword(String password, String salt, String hashedPassword);
}
