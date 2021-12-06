import entity.Account;
import entity.User;
import exceptions.InvalidCredentialException;
import exceptions.UserNotFoundException;
import repository.*;
import service.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class App {

    static Scanner scanner = new Scanner(System.in);

    // Interface
    static EntityManagerFactory entityManagerFactory=null;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("my-pu");
    }

    // Initialize
    static User currentUser = null;
    static Account currentAccount = null;

    // Dependencies
    static AccountRepository accountRepository = new JpaAccountRepository(entityManagerFactory);
    static TransferRepository transferRepository = new JpaTransferRepository(entityManagerFactory);
    static UserRepository userRepository = new JpaUserRepository(entityManagerFactory);

    // Dependents
    static AccountService accountService = new AccountServiceImpl(accountRepository);
    static TransferService transferService = new TransferServiceImpl(transferRepository);
    static UserService userService = new UserServiceImpl(userRepository);



    public static void main(String[] args) {

        //User Selection
        while (true) {
            System.out.println("\n Make a selection:");
            System.out.println("" +
                    "\n\n" + "1. Register \n" + "2. Login \n" + "3. Exit \n");
            int selection = scanner.nextInt();

            // modern switch statement for readability
            switch (selection) {
                case 1 -> registerNewAccount();
                case 2 -> newLogin();
                case 3 -> {
                    return;
                }
            }
        }
    }



    private static void registerNewAccount() {
        scanner.nextLine();
        System.out.println("First name: ");
        String firstName = scanner.nextLine();

        System.out.println("Last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        userService.registerUser(user);
    }

    private static void newLogin() {
        scanner.nextLine();
        System.out.println("Enter email: ");
        String email = scanner.nextLine();

        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        try {
            currentUser = userService.loginUser(email, password);
            accountService.setUser(currentUser);
            System.out.println("Login successful");

            boolean customerLoggedIn = true;

            while(customerLoggedIn) {
                System.out.println("" +
                        "\n\n" +
                        "1. View account \n" +
                        "2. Change email \n" +
                        "3. Transfer money \n" +
                        "4. View transactions \n" +
                        "5. Delete account \n" +
                        "6 Exit \n" +
                        "");

                int userChoice = scanner.nextInt();

                switch (userChoice) {
                    case 1 -> viewCurrentAccounts();
                    case 2 -> changeEmail();
                    case 3 -> newTransfer();
                    case 4 -> viewRecentTransactions();
                    case 5 -> deleteCurrentAccount();
                    case 6 -> exit();
                }
            }
        }
        catch (UserNotFoundException | InvalidCredentialException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    private static void viewCurrentAccounts() {
        accountService.showMyAccounts(currentUser.getId()).forEach((System.out::println));
    }

    private static void changeEmail() {
        System.out.println("Enter new email address: ");
        scanner.nextLine();
        String email = scanner.nextLine();

        userService.editAccountEmail(currentUser.getId(), email);
    }

    private static void newTransfer() {
        scanner.nextLine();
        System.out.println("Enter the account number you would like to transfer to: ");
        String toAccountNumber = scanner.nextLine();
        scanner.nextLine();

        System.out.println("Enter amount: ");
        double amount = scanner.nextDouble();

        String fromAccountNumber = accountService.findMyAccountNumber(currentUser.getId());
        transferService.transfer(amount, fromAccountNumber, toAccountNumber);
    }

    private static void viewRecentTransactions() {
        transferService.showMyTransactions(accountRepository.findMyAccountNumber(currentUser.getId())).forEach(System.out::println);
    }

    private static void deleteCurrentAccount() {
        System.out.println("Enter account number");
        String accountToDelete = scanner.nextLine();
        accountService.deleteAccount(accountToDelete);
    }

    private static void exit() {
        boolean customerLoggedIn = false;
    }
}
