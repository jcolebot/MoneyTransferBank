package service;

import entity.User;
import repository.UserRepository;

public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        userRepository.saveUser(user);
    }

    @Override
    public User loginUser(String email, String password) {
        return userRepository.login(email, password);
    }

    @Override
    public void editAccountEmail(int id, String newAccountEmail) {
        userRepository.updateAccountEmail(id, newAccountEmail);
    }
}