package service;

import entity.User;

public interface UserService {

    void registerUser(User user);

    User loginUser(String email, String password);

    void editAccountEmail(int id, String newAccEmail);

}
