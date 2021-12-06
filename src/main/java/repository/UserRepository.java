package repository;

import entity.User;
import entity.Transfer;
import java.util.List;

public interface UserRepository {

    void saveUser(User user);

    User login(String email, String password);

    User findUserByEmail(String email);

    void updateAccountEmail(int id, String newAccountEmail);

    List<User> showAllUsers();
}
