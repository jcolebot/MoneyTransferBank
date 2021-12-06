package service;

import entity.Transfer;
import java.util.List;

public interface TransferService {

    boolean transfer(double amount,String fromAccountNumber,String toAccountNumber);

    void saveTransfer(Transfer transfer);

    List<Transfer> showMyTransactions(String senderAccNumber);
}
