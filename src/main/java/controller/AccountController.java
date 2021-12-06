package controller;

import entity.Account;
import entity.User;
import io.javalin.http.Handler;
import repository.AccountRepository;
import repository.JpaAccountRepository;
import org.eclipse.jetty.http.HttpStatus;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class AccountController {

    static EntityManagerFactory entityManagerFactory;
    static {
        entityManagerFactory= Persistence.createEntityManagerFactory("my-pu");
    }

    static AccountRepository accountRepository =new JpaAccountRepository(entityManagerFactory);

    public static Handler postAccount = ctx ->{

    };

    public static Handler findAllAccount= ctx -> {
        List<Account> accounts = accountRepository.showAllAccounts();
        ctx.json(accounts);
    };

    public static Handler getMyAccount= ctx -> {
        int id=Integer.parseInt(ctx.pathParam("id"));
        Account account = accountRepository.findMyAccount(id);
        ctx.json(account);
    };

    public static Handler saveAccount = ctx -> {
        String userId=ctx.pathParam("userId");
        System.out.println(userId);
        Account account = ctx.bodyAsClass(Account.class);
        User user=new User();
        user.setId(Integer.parseInt(userId));
        account.setUser(user);
        accountRepository.saveAccount(account);
        ctx.status(HttpStatus.CREATED_201);
    };

    public static Handler deleteAccount= ctx -> {
        String id=(ctx.pathParam("accountNumber"));
        accountRepository.deleteAccount(id);
        ctx.status(HttpStatus.OK_200);
    };
}
