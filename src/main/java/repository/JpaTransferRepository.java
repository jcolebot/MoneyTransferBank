package repository;

import entity.Account;
import entity.Transfer;
import org.apache.log4j.Logger;
import exceptions.AccountNotFoundException;
import exceptions.BalanceException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class JpaTransferRepository implements TransferRepository {

    private static final Logger logger = Logger.getLogger("mts");
    private EntityManagerFactory entityManagerFactory;

    public JpaTransferRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void saveTransfer(Transfer transfer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(transfer);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public boolean transfer(double amount, String fromAccountNumber, String toAccountNumber) {
        Account fromAccount = loadAccount(fromAccountNumber).orElseThrow(
                () -> new AccountNotFoundException(fromAccountNumber)
        );

        Account toAccount = loadAccount(toAccountNumber).orElseThrow(
                () -> new AccountNotFoundException(toAccountNumber)
        );

        if(fromAccount.getBalance() < amount) {
            throw new BalanceException(fromAccount.getBalance());
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        logger.debug("debited");

        toAccount.setBalance(toAccount.getBalance() + amount);
        logger.debug("credited");

        updateAccountAmount(fromAccount);
        updateAccountAmount(toAccount);

        logger.info("transfer complete");

        Transfer transfer = new Transfer();

        transfer.setReceiverAccountNumber(toAccount.getAccountNumber());
        transfer.setAmount(amount);
        transfer.setDate(new Date());
        transfer.setAccount(fromAccount);

        saveTransfer(transfer);
        return false;
    }

    @Override
    public List<Transfer> findMyTransactions(String accountNumber) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        String jpql = "FROM Transfer WHERE sender_account_number=:accountNumber";
        Query query = entityManager.createQuery(jpql);
        List<Transfer> transfers = query.getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return transfers;
    }

    @Override
    public List<Transfer> findAllTransactions() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        String jpql = "from Transfer";
        Query query = entityManager.createQuery(jpql);
        List<Transfer> transfers = query.getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return transfers;
    }

    @Override
    public Optional<Account> loadAccount(String accountNumber) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        String jpql = "FROM Account WHERE accountNumber=:accountNumber";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("accountNumber", accountNumber);

        Account account = (Account)query.getSingleResult();

        entityManager.getTransaction().commit();
        entityManager.close();

        return Optional.of(account);
    }

    @Override
    public void updateAccountAmount(Account account) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.find(Account.class, account.getAccountNumber()).setBalance(account.getBalance());
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
