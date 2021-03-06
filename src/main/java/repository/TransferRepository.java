package repository;

import entity.Account;
import entity.Transfer;

import java.util.List;
import java.util.Optional;

public interface TransferRepository {

    void saveTransfer(Transfer transfer);

    boolean transfer(double amount, String fromAccountNumber, String toAccountNumber);

    List<Transfer> findMyTransactions(String accountNumber);

    List<Transfer> findAllTransactions();

    Optional<Account> loadAccount(String accountNumber);

    void updateAccountAmount(Account account);
}
