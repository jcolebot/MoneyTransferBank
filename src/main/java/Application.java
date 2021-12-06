import controller.AccountController;
import controller.TransferController;
import controller.UserController;
import io.javalin.Javalin;

public class Application {

    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
        }).start(8080);

        app.post("/users/{userId}/accounts", AccountController.saveAccount);

        app.post("/accounts", AccountController.postAccount);

        app.get("/users", UserController.findAllUsers);

        app.get("/users/{email}", UserController.findUserByEmail);

        app.post("/transactions", TransferController.makeTransfer);

        app.get("/transactions", TransferController.findAllTransactions);

        app.get("/accounts", AccountController.findAllAccount);

        app.get("/accounts/{id}", AccountController.getMyAccount);

        app.delete("/accounts/{accountNumber}", AccountController.deleteAccount);
    }
}
