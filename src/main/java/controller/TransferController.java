package controller;

import entity.Transfer;
import io.javalin.http.Handler;
import org.eclipse.jetty.http.HttpStatus;
import repository.JpaTransferRepository;
import repository.TransferRepository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class TransferController {

    static EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("my-pu");
    }

    static TransferRepository transferRepository = new JpaTransferRepository(entityManagerFactory);

    public static Handler makeTransfer = ctx -> {
        Transfer transfer=ctx.bodyAsClass(Transfer.class);
        transferRepository.saveTransfer(transfer);
        ctx.status(HttpStatus.CREATED_201);
    };

    public static Handler findAllTransactions = ctx -> {
        List<Transfer> transfers = transferRepository.findAllTransactions();
        ctx.json(transfers);
    };

    public static Handler getMyTransactions = ctx -> {

        String id = ctx.pathParam("accountNumber");
        List<Transfer> transfers = transferRepository.findMyTransactions(id);
        ctx.json(transfers);
    };
}
