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
        System.out.println("If you would like to view an Estimate of your total Account Balances in the future, simply type \"2\".");
        System.out.println("If you would like to view Debt reports, simply type \"3\".");
        System.out.println("If you would like to view a breakdown of your Spending vs. Saving, simply type \"4\".");
        System.out.println("If you would like to view an overview of your goals, simply type \"5\".");
        System.out.println("If you would like to view the Financial Sanity Report, simply type \"6\".");
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
                accountBalanceEstimate();
                return pageOperations();

            case 3:
                debtReporting();
                return pageOperations();

            case 4:
                spendingSavingReport();
                return pageOperations();

            case 5:
                goalsOverview();
                return pageOperations();

            case 6:
                financialSanityReport();
                return pageOperations();
                
            default:
                System.out.println("Invalid input, try again.");
                return pageOperations();
        }
    }

    private void financialSanityReport() {
    }

    private void goalsOverview() {
    }

    private void spendingSavingReport() {
    }

    private void debtReporting() {
    }

    private void accountBalanceEstimate() {
    }
}