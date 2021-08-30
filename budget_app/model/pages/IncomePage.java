package budget_app.model.pages;

import budget_app.data.Income;
import budget_app.data.User;
import budget_app.model.Page;
import budget_app.services.*;

import java.sql.SQLException;

public class IncomePage extends Page {
    User user;

    public IncomePage(User user) {
        this.user = user;
    }

    @Override
    public void printPage() {
        System.out.println("______________________________________________________________________________");
        System.out.println("Welcome to the income page! You'll find your income information below.");
        System.out.println();
        System.out.println("When you would like to move back to the main menu, simply type \"1\".");
        System.out.println("If you would like to close out of the program,simply type \"2\".");
        System.out.println("If you would like to add income sources, simply type \"3\".");
        System.out.println("If you would like to update income information, simply type \"4\".");
        System.out.println("If you would like to delete an income source, simply type \"5\".");
        System.out.println("______________________________________________________________________________");
    }

    @Override
    public Page pageOperations() {
        printPage();
        Income[] incomes = getIncomes();
        printIncomeInfo(incomes);
        printTotalIncome(incomes);
        int input = takeUserInputInt();
        switch (input) {
            case 1:
                return new MainMenu(user);

            case 2:
                return null;

            case 3:
                addIncomes();
                return pageOperations();

            case 4:
                updateIncome();
                return pageOperations();

            case 5:
                deleteIncome();
                return pageOperations();

            default:
                System.out.println("Invalid input, try again.");
                return pageOperations();
        }
    }

    private void deleteIncome() {
        SQLDeletes.deleteIncome(this.user);
    }

    private void updateIncome() {
        SQLUpdates.updateIncome(this.user);
    }

    private void addIncomes() {
        SQLInserts.addIncomes(this.user);
    }

    private void printTotalIncome(Income[] incomes){
        double totalIncome = Calculations.incomeTotal(incomes);
        System.out.print("Your total monthly income is: $");
        System.out.printf("%.2f", totalIncome);
        System.out.println();
    }

    private void printIncomeInfo(Income[] incomes) {
        for (Income i: incomes) {
            System.out.print("Income ID: " + i.getIncome_id() + " | Income Source: " + i.getIncome_source() +
                    " | " + "Frequency per month: " + i.getIncome_per_month() + " | One Time? ");
            if(i.isIs_one_time()) {
                System.out.print("Yes.");
            } else {
                System.out.print("No.");
            }
            System.out.println();
            System.out.println("Income Amount: $" + i.getIncome_amount());
            System.out.println("______________________________________________________________________________");
        }
    }

    private Income[] getIncomes() {
        Income[] incomes = SQLQueries.getIncomes(this.user);
        return incomes;
    }
}
