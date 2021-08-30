package budget_app.services;

import budget_app.data.Expense;
import budget_app.data.User;
import budget_app.controllers.UserInterface;
import budget_app.model.pages.ExpensePage;

import java.sql.SQLException;

public class SQLInserts {

    public static void addAccounts(User user){
        System.out.println("How many accounts would you like to add?");
        int numAccs = UserInterface.takeInputInt();
        DatabaseConnector databaseConnector = new DatabaseConnector();
        for(int i = 0; i < numAccs; i++) {
            try {
                // take user input, prepare the sql insert statement, and execute the statement to insert the account
                System.out.println("To cancel, type \"cancel\" into account name");
                System.out.println("Account Name: ");
                String accountName = UserInterface.takeInputString();
                if(accountName.compareToIgnoreCase("cancel") == 0){
                    i = numAccs;
                    return;
                }
                System.out.println("Bank Name: ");
                String bankName = UserInterface.takeInputString();
                System.out.println("Account Balance: ");
                System.out.print("$");
                double accountBalance = UserInterface.takeInputDouble();

                // INSERT INTO orders ( userid, timestamp)
                // SELECT o.userid , o.timestamp FROM users u INNER JOIN orders o ON  o.userid = u.id

                String sqlInsert = "INSERT INTO accounts(account_name, bank_name, account_balance, user_id) VALUES(?, ?, ?, ?)";

                databaseConnector.openConnection();

                databaseConnector.preparedStatement = databaseConnector.prepareStatement(sqlInsert);
                databaseConnector.preparedStatement.setString(1, accountName);
                databaseConnector.preparedStatement.setString(2, bankName);
                databaseConnector.preparedStatement.setDouble(3, accountBalance);
                databaseConnector.preparedStatement.setInt(4, user.getUser_id());

                databaseConnector.executePreparedInsertStatement();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnector.closeConnection();
            }
        }
    }

    public static void addCustomGoals(User user){
        System.out.println("How many Goals would you like to add?");
        int numGoals = UserInterface.takeInputInt();
        DatabaseConnector databaseConnector = new DatabaseConnector();
        for(int i = 0; i < numGoals; i++) {
            try {
                // take user input, prepare the sql insert statement, and execute the statement to insert the account
                System.out.println("To cancel, type \"cancel\" into Goal name.");
                System.out.println("Goal name: ");
                String goalName = UserInterface.takeInputString();
                if(goalName.compareToIgnoreCase("cancel") == 0){
                    i = numGoals;
                    return;
                }
                System.out.print("Total amount needed to save: $");
                double totalNeeded = UserInterface.takeInputDouble();
                System.out.println();
                System.out.print("Monthly contribution amount: $");
                double monthlyContribution = 0;
                boolean unconfirmed = true;
                while(unconfirmed) {
                    monthlyContribution = UserInterface.takeInputDouble();
                    if(monthlyContribution >= 0 && monthlyContribution <= totalNeeded){
                        unconfirmed = false;
                    } else {
                        System.out.println("Please choose a valid number between 0 and the total amount needed.");
                    }
                }
                System.out.println();
                System.out.print("Current amount saved (if any): $");
                double amountSaved = 0;
                unconfirmed = true;
                while(unconfirmed) {
                    amountSaved = UserInterface.takeInputDouble();
                    if(amountSaved >= 0 && amountSaved <= totalNeeded){
                        unconfirmed = false;
                    } else {
                        System.out.println("Please choose a valid number between 0 and the total amount needed.");
                    }
                }
                System.out.println();




                String sqlInsert = "INSERT INTO custom_goals(goal_name, total_needed, monthly_contribution, " +
                        "amount_saved, user_id) VALUES(?, ?, ?, ?, ?)";

                databaseConnector.openConnection();

                databaseConnector.preparedStatement = databaseConnector.prepareStatement(sqlInsert);
                databaseConnector.preparedStatement.setString(1, goalName);
                databaseConnector.preparedStatement.setDouble(2, totalNeeded);
                databaseConnector.preparedStatement.setDouble(3,monthlyContribution);
                databaseConnector.preparedStatement.setDouble(4,amountSaved);
                databaseConnector.preparedStatement.setInt(5, user.getUser_id());

                databaseConnector.executePreparedInsertStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnector.closeConnection();
            }
        }
    }

    public static void addDebts(User user){
        System.out.println("How many Debts would you like to add?");
        int numDebts = UserInterface.takeInputInt();
        DatabaseConnector databaseConnector = new DatabaseConnector();
        for(int i = 0; i < numDebts; i++) {
            try {
                // take user input, prepare the sql insert statement, and execute the statement to insert the account
                System.out.println("To cancel, type \"cancel\" into Debt name.");
                System.out.println("Debt name: ");
                String debtName = UserInterface.takeInputString();
                if(debtName.compareToIgnoreCase("cancel") == 0){
                    i = numDebts;
                    return;
                }
                System.out.println("Debt amount: ");
                System.out.print("$");
                double debtAmount = UserInterface.takeInputDouble();
                System.out.println();
                System.out.print("Monthly payment amount: $");
                double monthlyPayment  = UserInterface.takeInputDouble();
                System.out.println();
                System.out.print("Interest Rate (as %): ");
                double debtInterestRate = 0;
                boolean unconfirmed = true;
                while(unconfirmed) {
                    debtInterestRate = UserInterface.takeInputDouble();
                    if(debtInterestRate >= 0 && debtInterestRate <= 100){
                        unconfirmed = false;
                    } else {
                        System.out.println("Please choose a valid percentage between 0 and 100.");
                    }
                }
                System.out.println();
                System.out.print("Lender Name: ");
                String lenderName = UserInterface.takeInputString();
                System.out.println();
                System.out.print("Day of the month that the debt is due: ");
                int debtDueDate = 1;
                unconfirmed = true;
                while(unconfirmed) {
                    debtDueDate = UserInterface.takeInputInt();
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

    public static void addExpenses(User user){
        System.out.println("How many Monthly Expenses would you like to add?");
        int numExpenses = UserInterface.takeInputInt();
        DatabaseConnector databaseConnector = new DatabaseConnector();
        for(int i = 0; i < numExpenses; i++) {
            try {
                // take user input, prepare the sql insert statement, and execute the statement to insert the account
                System.out.println("To cancel, type \"cancel\" into Expense Name.");
                System.out.println("Expense Name: ");
                String expenseName = UserInterface.takeInputString();
                if(expenseName.compareToIgnoreCase("cancel") == 0){
                    i = numExpenses;
                    return;
                }
                System.out.println("Expense amount: ");
                System.out.print("$");
                double expenseAmount = UserInterface.takeInputDouble();
                System.out.println();

                Expense.Categories cat = ExpensePage.getCategoryFromUser();
                String categoryString = Expense.getStringFromCategory(cat);


                String sqlInsert = "INSERT INTO expenses(expense_name, expense_category, " +
                        "expense_amount, user_id) VALUES(?, ?, ?, ?)";

                databaseConnector.openConnection();

                databaseConnector.preparedStatement = databaseConnector.prepareStatement(sqlInsert);
                databaseConnector.preparedStatement.setString(1, expenseName);
                databaseConnector.preparedStatement.setString(2, categoryString);
                databaseConnector.preparedStatement.setDouble(3, expenseAmount);
                databaseConnector.preparedStatement.setInt(4, user.getUser_id());

                databaseConnector.executePreparedInsertStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnector.closeConnection();
            }
        }
    }

    public static void addIncomes(User user){
        System.out.println("How many income sources would you like to add?");
        int numIncomes = UserInterface.takeInputInt();
        DatabaseConnector databaseConnector = new DatabaseConnector();
        for(int i = 0; i < numIncomes; i++) {
            try {
                // take user input, prepare the sql insert statement, and execute the statement to insert the account
                System.out.println("To cancel, type \"cancel\" into income source.");
                System.out.println("Income source: ");
                String incomeSource = UserInterface.takeInputString();
                if(incomeSource.compareToIgnoreCase("cancel") == 0){
                    i = numIncomes;
                    return;
                }
                System.out.println("Income amount: ");
                System.out.print("$");
                double incomeAmt = UserInterface.takeInputDouble();
                System.out.print("Is this a one time income? (Type yes or no): ");
                boolean unconfirmed = true;
                int incOT = 0;
                while(unconfirmed){
                    String userInput = UserInterface.takeInputString();
                    if(userInput.compareToIgnoreCase("yes") == 0){
                        incOT = 1;
                        unconfirmed = false;
                    } else if(userInput.compareToIgnoreCase("no")==0){
                        incOT = 0;
                        unconfirmed = false;
                    } else {
                        System.out.println("Please only type \"yes\" or \"no\".");
                        System.out.print("Is this a one time income? (Type yes or no): ");
                    }
                }
                System.out.print("How many times per month do you receive this paycheck? (Enter 0 if one time) | ");
                int income_per_month = UserInterface.takeInputInt();

                // INSERT INTO orders ( userid, timestamp)
                // SELECT o.userid , o.timestamp FROM users u INNER JOIN orders o ON  o.userid = u.id

                String sqlInsert = "INSERT INTO income(income_source, income_amount, " +
                        "is_one_time, income_per_month, user_id) VALUES(?, ?, ?, ?, ?)";

                databaseConnector.openConnection();

                databaseConnector.preparedStatement = databaseConnector.prepareStatement(sqlInsert);
                databaseConnector.preparedStatement.setString(1, incomeSource);
                databaseConnector.preparedStatement.setDouble(2, incomeAmt);
                databaseConnector.preparedStatement.setInt(3, incOT);
                databaseConnector.preparedStatement.setInt(4,income_per_month);
                databaseConnector.preparedStatement.setInt(5, user.getUser_id());

                databaseConnector.executePreparedInsertStatement();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnector.closeConnection();
            }
        }
    }

    public static void addUser(User user){
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try{
            databaseConnector.openConnection();
            String sql = "INSERT INTO Users(first_name, last_name, username, password) VALUES (?, ?, ?, ?)";
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
            databaseConnector.preparedStatement.setString(1, user.getFirst_name());
            databaseConnector.preparedStatement.setString(2, user.getLast_name());
            databaseConnector.preparedStatement.setString(3, user.getUsername());
            databaseConnector.preparedStatement.setString(4, user.getPassword());
            databaseConnector.executePreparedInsertStatement();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
    }

}
