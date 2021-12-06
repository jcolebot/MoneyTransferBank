package exceptions;

public class BalanceException extends RuntimeException {

    public BalanceException(double balance) {
        super(String.valueOf(balance));
    }
}
