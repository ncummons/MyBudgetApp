package budget_app.model.pages;

import budget_app.data.Debt;
import budget_app.data.User;
import budget_app.model.Page;
import budget_app.services.*;

import java.sql.SQLException;

public class DebtPage extends Page {

    User user;

    public DebtPage(User user) {
        this.user = user;
    }

    @Override
    public void printPage() {
        System.out.println("______________________________________________________________________________");
        System.out.println("Welcome to the Debt Information Page! You'll find your debt information below.");
        System.out.println();
        System.out.println("When you would like to move back to the main menu, simply type \"1\".");
        System.out.println("If you would like to close out of the program,simply type \"2\".");
        System.out.println("If you would like to add a new debt, simply type \"3\".");
        System.out.println("If you would like to update debt information, simply type \"4\".");
        System.out.println("If you would like to delete an debt, simply type \"5\".");
        System.out.println("______________________________________________________________________________");
    }

    @Override
    public Page pageOperations() {
        printPage();
        Debt[] debts = getDebts();
        printDebtInfo(debts);
        printTotalDebt(debts);
        int input = takeUserInputInt();
        switch (input){
            case 1: return new MainMenu(user);

            case 2: return null;

            case 3: addDebts(); return pageOperations();

            case 4: updateDebt(); return pageOperations();

            case 5: deleteDebt(); return pageOperations();

            default:
                System.out.println("Invalid input, try again.");
                return pageOperations();
        }
    }

    private void deleteDebt() {
        SQLDeletes.deleteDebt(this.user);
    }

    private void updateDebt() {
        SQLUpdates.updateDebt(this.user);
    }

    private void addDebts() {
        SQLInserts.addDebts(this.user);
    }

    private void printTotalDebt(Debt[] debts) {
        double totalDebt = Calculations.debtsTotal(debts);
        System.out.print("Your total debt is: $");
        System.out.printf("%.2f", totalDebt);
        System.out.println();
    }

    private void printDebtInfo(Debt[] debts) {
        for (Debt d: debts) {
            System.out.print("Debt ID: " + d.getDebt_id() + " | Debt Name: " + d.getDebt_name() +
                    " | " + "Debt Interest Rate: " + d.getInterest_rate() + "% | Lender Name: " + d.getLender_name());
            System.out.println();
            System.out.println("Debt Amount: $" + d.getAmount() + " | Monthly Payment: $" + d.getMonthly_payment() +
                    " | Day of the month due: " + d.getDebt_due_date() + " |");
            System.out.println("______________________________________________________________________________");
        }
    }

    private Debt[] getDebts() {
        Debt[] debts = SQLQueries.getDebts(this.user);
        return debts;
    }
}
