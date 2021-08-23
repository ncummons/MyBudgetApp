package budget_app.model.pages;

import budget_app.data.Debt;
import budget_app.data.Expense;
import budget_app.data.User;
import budget_app.model.Page;
import budget_app.services.DatabaseConnector;

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
        DatabaseConnector databaseConnector = new DatabaseConnector();
        int debtID;
        System.out.println("Which Debt would you like to delete? Enter the corresponding debt id below.");
        try {
            debtID = takeUserInputInt();
            String sql = "DELETE FROM debts WHERE debt_id = " + debtID + ";";
            databaseConnector.openConnection();
            databaseConnector.executeDeleteStatement(sql);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
    }

    private void updateDebt() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        int debtID;
        System.out.println("Which Debt would you like to update? Enter the corresponding debt id below: ");
        try {
            debtID = takeUserInputInt();
            databaseConnector.openConnection();
            databaseConnector.resultSet = databaseConnector.executeQuery("SELECT debts.* FROM debts " +
                    "WHERE debts.debt_id = " + debtID + ";");

            if(databaseConnector.resultSet == null){
                System.out.println("The debt does not exist. Please try again.");
                return;
            }
            // Create the object of the account
            databaseConnector.resultSet.next();
            Debt debt = new Debt();
            debt.setUser_id(user.getUser_id());
            debt.setDebt_id(databaseConnector.resultSet.getInt("debt_id"));
            debt.setDebt_name(databaseConnector.resultSet.getString("debt_name"));
            debt.setMonthly_payment(databaseConnector.resultSet.getDouble("monthly_payment"));
            debt.setAmount(databaseConnector.resultSet.getDouble("amount"));
            debt.setInterest_rate(databaseConnector.resultSet.getDouble("interest_rate"));
            debt.setDebt_length_months(databaseConnector.resultSet.getInt("debt_length_months"));
            debt.setLender_name(databaseConnector.resultSet.getString("lender_name"));
            debt.setDebt_due_date(databaseConnector.resultSet.getInt("debt_due_date"));

            // Take user input for what to update, then update it
            System.out.print("Debt name: ");
            String debtName = takeUserInputString();
            System.out.println();
            System.out.print("Debt amount: $");
            double debtAmount  = takeUserInputDouble();
            System.out.println();
            System.out.print("Monthly payment amount: $");
            double monthlyPayment  = takeUserInputDouble();
            System.out.println();
            System.out.print("Debt Interest Rate (as %): ");
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
            System.out.print("Length of the debt in months: ");
            int debtLength = takeUserInputInt();
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

            databaseConnector.executeUpdateStatement("UPDATE debts " +
                    "SET debt_name = \"" + debtName + "\", amount = " + debtAmount + ", interest_rate = " +
                    debtInterestRate + ", monthly_payment = " + monthlyPayment +
                    ", debt_length_months = " + debtLength + ", debt_due_date = " + debtDueDate +
                    ", lender_name = \"" + lenderName + "\" WHERE debts.debt_id = " + debtID + ";");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem updating your debt. Please try again later.");
        }finally {
            databaseConnector.closeConnection();
        }
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
                System.out.print("Length of the debt in months: ");
                int debtLength = takeUserInputInt();
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



                String sqlInsert = "INSERT INTO debts(debt_name, amount, monthly_payment, interest_rate, debt_length_months, " +
                        "lender_name, debt_due_date, user_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

                databaseConnector.openConnection();

                databaseConnector.preparedStatement = databaseConnector.prepareInsertStatement(sqlInsert);
                databaseConnector.preparedStatement.setString(1, debtName);
                databaseConnector.preparedStatement.setDouble(2, debtAmount);
                databaseConnector.preparedStatement.setDouble(3,monthlyPayment);
                databaseConnector.preparedStatement.setDouble(4,debtInterestRate);
                databaseConnector.preparedStatement.setInt(5, debtLength);
                databaseConnector.preparedStatement.setString(6,lenderName);
                databaseConnector.preparedStatement.setInt(7, debtDueDate);
                databaseConnector.preparedStatement.setInt(8, user.getUser_id());

                databaseConnector.executePreparedStatement();
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
                    " | Day of the month due: " + d.getDebt_due_date() + " | Months left to pay: " + d.getDebt_length_months());
            System.out.println("______________________________________________________________________________");
        }
    }

    private Debt[] getDebts() {
        Debt[] debts = null;
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {

            // Get the number of expenses from the query, to dynamically increase or decrease the size of the array
            databaseConnector.openConnection();
            databaseConnector.resultSet = databaseConnector.executeQuery("SELECT COUNT(debts.debt_id) FROM debts" +
                    " INNER JOIN users" +
                    " ON users.user_id = debts.user_id " +
                    "WHERE users.user_id = " + user.getUser_id() + ";");
            databaseConnector.resultSet.next();
            int numDebts = databaseConnector.resultSet.getInt("COUNT(debts.debt_id)");
            databaseConnector.closeConnection();

            // Query the accounts and map them to account objects, then populate array

            debts = new Debt[numDebts]; // Accounts Array to be returned

            databaseConnector.openConnection();
            databaseConnector.resultSet = databaseConnector.executeQuery("SELECT debts.* FROM debts" +
                    " INNER JOIN users" +
                    " ON users.user_id = debts.user_id " +
                    "WHERE users.user_id = " + user.getUser_id() + ";");
            int i = 0;
            while (databaseConnector.resultSet.next()) {
                Debt debt = new Debt();
                debt.setDebt_id(databaseConnector.resultSet.getInt("debt_id"));
                debt.setDebt_name(databaseConnector.resultSet.getString("debt_name"));
                debt.setAmount(databaseConnector.resultSet.getDouble("amount"));
                debt.setMonthly_payment(databaseConnector.resultSet.getDouble("monthly_payment"));
                debt.setInterest_rate(databaseConnector.resultSet.getDouble("interest_rate"));
                debt.setDebt_length_months(databaseConnector.resultSet.getInt("debt_length_months"));
                debt.setDebt_due_date(databaseConnector.resultSet.getInt("debt_due_date"));
                debt.setLender_name(databaseConnector.resultSet.getString("lender_name"));
                debt.setUser_id(user.getUser_id());
                debts[i] = debt;
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
        return debts;
    }
}
