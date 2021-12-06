package service;

import entity.Transfer;
import repository.TransferRepository;
import org.apache.log4j.Logger;
import java.util.List;

public class TransferServiceImpl implements TransferService{

    private static final Logger logger = Logger.getLogger("mts");

    private TransferRepository transferRepository;


    public TransferServiceImpl(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Override
    public boolean transfer(double amount, String fromAccountNumber, String toAccountNumber) {
        return transferRepository.transfer(amount, fromAccountNumber, toAccountNumber);
    }

    @Override
    public void saveTransfer(Transfer transfer) {
        transferRepository.saveTransfer(transfer);
    }

    @Override
    public List<Transfer> showMyTransactions(String senderAccNumber) {
        return transferRepository.findMyTransactions(senderAccNumber);
    }
}
