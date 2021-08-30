package budget_app.model.pages;

import budget_app.data.Debt;
import budget_app.data.User;
import budget_app.model.Page;
import budget_app.services.DatabaseConnector;
import budget_app.services.SQLDeletes;
import budget_app.services.SQLQueries;
import budget_app.services.SQLUpdates;

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
        System.out.println("How many Debts would you like to add?");
        int numDebts = takeUserInputInt();
        DatabaseConnector databaseConnector = new DatabaseConnector();
        for(int i = 0; i < numDebts; i++) {
            try {
                // take user input, prepare the sql insert statement, and execute the statement to insert the account
                System.out.println("To cancel, type \"cancel\" into Debt name.");
                System.out.println("Debt name: ");
                String debtName = takeUserInputString();
                if(debtName.compareToIgnoreCase("cancel") == 0){
                    i = numDebts;
                    return;
                }
                System.out.println("Debt amount: ");
                System.out.print("$");
                double debtAmount = takeUserInputDouble();
                System.out.println();
                System.out.print("Monthly payment amount: $");
                double monthlyPayment  = takeUserInputDouble();
                System.out.println();
                System.out.print("Interest Rate (as %): ");
                double debtInterestRate = 0;
                boolean unconfirmed = true;
                while(unconfirmed) {
                    debtInterestRate = takeUserInputDouble();
                    if(debtInterestRate >= 0 && debtInterestRate <= 100){
                        unconfirmed = false;
                    } else {
                        System.out.println("Please choose a valid percentage between 0 and 100.");
                    }
                }
                System.out.println();
                System.out.print("Lender Name: ");
                String lenderName = takeUserInputString();
                System.out.println();
                System.out.print("Day of the month that the debt is due: ");
                int debtDueDate = 1;
                unconfirmed = true;
                while(unconfirmed) {
                    debtDueDate = takeUserInputInt();
                    if(debtDueDate > 0 && debtDueDate < 31){
                        unconfirmed = false;
                    } else {
                        System.out.println("Please choose a valid date between 1 and 30.");
                    }
                }



                String sqlInsert = "INSERT INTO debts(debt_name, amount, monthly_payment, interest_rate, " +
                        "lender_name, debt_due_date, user_id) VALUES(?, ?, ?, ?, ?, ?, ?)";

                databaseConnector.openConnection();

                databaseConnector.preparedStatement = databaseConnector.prepareStatement(sqlInsert);
                databaseConnector.preparedStatement.setString(1, debtName);
                databaseConnector.preparedStatement.setDouble(2, debtAmount);
                databaseConnector.preparedStatement.setDouble(3,monthlyPayment);
                databaseConnector.preparedStatement.setDouble(4,debtInterestRate);
                databaseConnector.preparedStatement.setString(5,lenderName);
                databaseConnector.preparedStatement.setInt(6, debtDueDate);
                databaseConnector.preparedStatement.setInt(7, user.getUser_id());

                databaseConnector.executePreparedInsertStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnector.closeConnection();
            }
        }
    }

    private void printTotalDebt(Debt[] debts) {
        double totalDebt = 0;
        for (Debt d: debts) {
            totalDebt += d.getAmount();
        }
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
