package budget_app.model.pages;

import budget_app.data.CustomGoal;
import budget_app.data.User;
import budget_app.model.Page;

public class ReportsPage extends Page {
    User user;

    public ReportsPage(User user) {
        this.user = user;
    }

    @Override
    public void printPage() {
        System.out.println("______________________________________________________________________________");
        System.out.println("Welcome to the Financial Reports Page, where you can get an overview of your financials!");
        System.out.println();
        System.out.println("When you would like to move back to the main menu, simply type \"1\".");
        System.out.println("If you would like to close out of the program,simply type \"2\".");
        System.out.println("If you would like to add a new custom goal, simply type \"3\".");
        System.out.println("If you would like to update custom goal information, simply type \"4\".");
        System.out.println("If you would like to delete a custom goal, simply type \"5\".");
        System.out.println("______________________________________________________________________________");
    }

    @Override
    public Page pageOperations() {
        printPage();
        int input = takeUserInputInt();
        switch (input) {
            case 1:
                return new MainMenu(user);

            case 2:
                return null;

            case 3:


            case 4:


            case 5:

            default:
                System.out.println("Invalid input, try again.");
                return pageOperations();
        }
    }
}
