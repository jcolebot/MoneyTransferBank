package repository;

import entity.Account;
import java.util.List;


public interface AccountRepository {

    void saveAccount(Account account);

    void deleteAccount(String id);

    String findMyAccountNumber(int userId);

    Account findMyAccount(int userId);

    List<Account> showMyAccount (int userId);

    List <Account> showAllAccounts();

    Account loadAccount(String account);
}
