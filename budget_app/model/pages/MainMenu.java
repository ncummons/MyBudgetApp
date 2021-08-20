package budget_app.model.pages;

import budget_app.controllers.UserInterface;
import budget_app.data.User;
import budget_app.model.Application;
import budget_app.model.Page;

public class MainMenu extends Page {
    User user;
    UserInterface userInterface = new UserInterface();

    public MainMenu(User user) {
        this.user = user;
    }

    public Page pageOperations(){
        printPage();
        int input = takeUserInputInt();
        switch (input){
            case 1: return new AccountsPage(user);

            case 2: return new IncomePage(user);

            case 3: return new ExpensePage(user);

            case 4: return new DebtPage(user);

            case 5: return new ReportsPage(user);

            case 6: return new CustomGoalsPage(user);

            case 7: return new UserInformationPage(user);

            case 8: return null;

            default:
                System.out.println("Incorrect input, please try again.");
                pageOperations();
                return null;
        }
    }

    @Override
    public void printPage() {
        System.out.println("Welcome to Budget Central, " + user.getFirst_name() + "!");
        System.out.println("What would you like to see today? Please " +
                "select an option by typing in the corresponding number.");
        System.out.println("1) Account Information");
        System.out.println("2) Income Information");
        System.out.println("3) Expense Information");
        System.out.println("4) Debt Information");
        System.out.println("5) Reports Page");
        System.out.println("6) Custom Goals Page");
        System.out.println("7) User Information Page");
        System.out.println("8) Close Application");
    }

}
