package entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")

public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Transaction_Number")
    private int id;

    @Column(name = "Receiver_Account_Number",  nullable = false)
    private String receiverAccountNumber;

    @Column(name = "Amount", nullable = false)
    private double amount;

    @Column(name = "Date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Sender_Account_Number")
    private Account account;

    public Transfer(String receiverAccountNumber, double amount, Date date) {
        this.receiverAccountNumber = receiverAccountNumber;
        this.amount = amount;
        this.date = date;
    }

    public Transfer(String receiverAccountNumber, double amount, Date date, Account account) {
        this.receiverAccountNumber = receiverAccountNumber;
        this.amount = amount;
        this.date = date;
        this.account = account;
    }

    public Transfer() {

    }

    public int getId() {
        return id;
    }

    public void setId() {
        this.id = id;
    }

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Transfer{" + "id=" + id + ", receiverAccountNumber='" + receiverAccountNumber +
                '\'' + ", amount=" + amount + ", date=" + date + ", account=" + account + '}';
    }
}
