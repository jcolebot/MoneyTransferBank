package controller;

import entity.User;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import repository.JpaUserRepository;
import repository.UserRepository;
import org.eclipse.jetty.http.HttpStatus;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class UserController {

    static EntityManagerFactory entityManagerFactory;
    static {
        entityManagerFactory= Persistence.createEntityManagerFactory("my-pu");
    }

    static UserRepository userRepository = new JpaUserRepository(entityManagerFactory);

    public static Handler findAllUsers = ctx -> {
        List<User> users = userRepository.showAllUsers();
        ctx.json(users);
    };

    public static Handler findUserByEmail = ctx -> {
        String email = ctx.pathParam("email");
        User user = userRepository.findUserByEmail(email);
        ctx.json(user);
    };

    public static Handler createNewUser= ctx -> {
        String firstName= ctx.pathParam("First_Name");
        String lastName=ctx.pathParam("Last_Name");
        String email=ctx.pathParam("email");
        String password=ctx.pathParam("password");
        User user=new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);

        userRepository.saveUser(user);
        ctx.json(user);
    };
}
