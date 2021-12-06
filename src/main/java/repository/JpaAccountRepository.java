package repository;

import entity.Account;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Connection;
import java.util.List;

public class JpaAccountRepository implements AccountRepository {

    private EntityManagerFactory entityManagerFactory;

    public JpaAccountRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void saveAccount(Account account) {
        var accountNumber = "RE" + makeIdNumber(14);
        account.setAccountNumber(accountNumber);
        System.out.println(account.getBalance() + "-----------------------------------");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(account);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void deleteAccount(String id) {

    }

    @Override
    public String findMyAccountNumber(int userId) {
        return null;
    }

    @Override
    public Account findMyAccount(int userId) {
        return null;
    }

    @Override
    public List<Account> showMyAccount(int userId) {
        return null;
    }

    @Override
    public List<Account> showAllAccounts() {
        return null;
    }

    // Randomize account number
    private String makeIdNumber(int length) {
        var result = "";
        var characters = "0123456789";
        var charactersLength = characters.length();

        for(var i = 0; i < length; i++) {
            result = result + characters.charAt((int) Math.floor(Math.random() * charactersLength));
        }
        return result;
    }
}
