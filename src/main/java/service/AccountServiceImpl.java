package service;

import entity.Account;
import entity.User;
import repository.AccountRepository;
import java.util.List;



public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private User user;
    private Account account;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void addAccount(Account account){
        accountRepository.saveAccount(account);
    }

    @Override
    public void deleteAccount(String id) {
        accountRepository.deleteAccount(id);
    }

    @Override
    public List<Account> showAllAccounts() {
        return accountRepository.showAllAccounts();

    }

    @Override
    public List<Account> showMyAccounts(int userId) {
        return accountRepository.showMyAccount(userId);
    }

    @Override
    public String findMyAccountNumber(int userId) {
        return accountRepository.findMyAccountNumber(userId);
    }
}
