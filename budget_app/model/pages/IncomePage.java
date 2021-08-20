package budget_app.model.pages;

import budget_app.data.Account;
import budget_app.data.Expense;
import budget_app.data.Income;
import budget_app.data.User;
import budget_app.model.Page;
import budget_app.services.DatabaseConnector;

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
        DatabaseConnector databaseConnector = new DatabaseConnector();
        int incID;
        System.out.println("Which Income would you like to delete? Enter the corresponding income id below.");
        try {
            incID = takeUserInputInt();
            String sql = "DELETE FROM income WHERE income_id = " + incID + ";";
            databaseConnector.openConnection();
            databaseConnector.executeDeleteStatement(sql);
        } catch (
                Exception e) {
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
    }

    private void updateIncome() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        int incID;
        System.out.println("Which Income would you like to update? Enter the corresponding income id below: ");
        try {
            incID = takeUserInputInt();
            databaseConnector.openConnection();
            databaseConnector.resultSet = databaseConnector.executeQuery("SELECT income.* FROM income" +
                    " INNER JOIN users" +
                    " ON users.user_id = income.user_id " +
                    "WHERE income.income_id = " + incID + ";");
            if(databaseConnector.resultSet == null){
                System.out.println("The income does not exist. Please try again.");
                return;
            }
            // Create the object of the account
            databaseConnector.resultSet.next();
            Income inc = new Income();
            inc.setUser_id(user.getUser_id());
            inc.setIncome_id(databaseConnector.resultSet.getInt("income_id"));
            inc.setIncome_amount(databaseConnector.resultSet.getDouble("income_amount"));
            inc.setIncome_source(databaseConnector.resultSet.getString("income_source"));
            inc.setIncome_weekly_interval(databaseConnector.resultSet.getInt("income_weekly_interval"));
            int boolInt = databaseConnector.resultSet.getInt("is_one_time");
            if(boolInt == 1) {
                inc.setIs_one_time(true);
            }else{
                inc.setIs_one_time(false);
            }

            // Take user input for what to update, then update it
            System.out.print("Income source: ");
            String incSource = takeUserInputString();
            System.out.println();
            System.out.print("Income amount: $");
            double incAmount  = takeUserInputDouble();
            System.out.println();
            System.out.print("Is this a one time income? (Type yes or no): ");
            boolean unconfirmed = true;
            int incOT = 0;
            while(unconfirmed){
                String userInput = takeUserInputString();
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
            System.out.println();
            System.out.print("What is the weekly interval between paychecks? | ");
            int weeklyInterval = takeUserInputInt();

            int rows = databaseConnector.executeUpdateStatement("UPDATE income " +
                    "SET income_source = \"" + incSource + "\", income_amount = " + incAmount + ", " +
                    "is_one_time = " + incOT + ", income_weekly_interval = " + weeklyInterval +
                    " WHERE income.income_id = " + incID + ";");
            if(rows != 0){
                System.out.println("Update successful.");
            } else {
                System.out.println("There was a problem updating your income. Please try again later.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem updating your income. Please try again later.");
        }finally {
            databaseConnector.closeConnection();
        }
    }

    private void addIncomes() {
        System.out.println("How many income sources would you like to add?");
        int numIncomes = takeUserInputInt();
        DatabaseConnector databaseConnector = new DatabaseConnector();
        for(int i = 0; i < numIncomes; i++) {
            try {
                // take user input, prepare the sql insert statement, and execute the statement to insert the account
                System.out.println("To cancel, type \"cancel\" into income source.");
                System.out.println("Income source: ");
                String incomeSource = takeUserInputString();
                if(incomeSource.compareToIgnoreCase("cancel") == 0){
                    i = numIncomes;
                    return;
                }
                System.out.println("Income amount: ");
                System.out.print("$");
                double incomeAmt = takeUserInputDouble();
                System.out.print("Is this a one time income? (Type yes or no): ");
                boolean unconfirmed = true;
                int incOT = 0;
                while(unconfirmed){
                    String userInput = takeUserInputString();
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
                System.out.print("What is the weekly interval between paychecks? (Enter 0 if one time)");
                int weeklyInterval = takeUserInputInt();

                // INSERT INTO orders ( userid, timestamp)
                // SELECT o.userid , o.timestamp FROM users u INNER JOIN orders o ON  o.userid = u.id

                String sqlInsert = "INSERT INTO income(income_source, income_amount, " +
                        "is_one_time, income_weekly_interval, user_id) VALUES(?, ?, ?, ?, ?)";

                databaseConnector.openConnection();

                databaseConnector.preparedStatement = databaseConnector.prepareInsertStatement(sqlInsert);
                databaseConnector.preparedStatement.setString(1, incomeSource);
                databaseConnector.preparedStatement.setDouble(2, incomeAmt);
                databaseConnector.preparedStatement.setInt(3, incOT);
                databaseConnector.preparedStatement.setInt(4,weeklyInterval);
                databaseConnector.preparedStatement.setInt(5, user.getUser_id());

                databaseConnector.executePreparedStatement();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnector.closeConnection();
            }
        }
    }

    private void printTotalIncome(Income[] incomes){
        double totalIncome = 0;
        for (Income i: incomes) {
            totalIncome += i.getIncome_amount();
        }
        System.out.print("Your total income is: $");
        System.out.printf("%.2f", totalIncome);
        System.out.println();
    }

    private void printIncomeInfo(Income[] incomes) {
        for (Income i: incomes) {
            System.out.print("Income ID: " + i.getIncome_id() + " | Income Source: " + i.getIncome_source() +
                    " | " + "Income Weekly Interval: " + i.getIncome_weekly_interval() + " | One Time? ");
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
        Income[] incomes = null;
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {

            // Get the number of incomes from the query, to dynamically increase or decrease the size of the array
            databaseConnector.openConnection();
            databaseConnector.resultSet = databaseConnector.executeQuery("SELECT COUNT(income.income_id) FROM income" +
                    " INNER JOIN users" +
                    " ON users.user_id = income.user_id " +
                    "WHERE users.user_id = " + user.getUser_id() + ";");
            databaseConnector.resultSet.next();
            int numIncomes = databaseConnector.resultSet.getInt("COUNT(income.income_id)");
            databaseConnector.closeConnection();

            // Query the accounts and map them to account objects, then populate array

            incomes = new Income[numIncomes]; // Accounts Array to be returned

            databaseConnector.openConnection();
            databaseConnector.resultSet = databaseConnector.executeQuery("SELECT income.* FROM income" +
                    " INNER JOIN users" +
                    " ON users.user_id = income.user_id " +
                    "WHERE users.user_id = " + user.getUser_id() + ";");
            int i = 0;
            while (databaseConnector.resultSet.next()) {
                Income inc = new Income();
                inc.setIncome_id(databaseConnector.resultSet.getInt("income_id"));
                inc.setIncome_source(databaseConnector.resultSet.getString("income_source"));
                inc.setIncome_amount(databaseConnector.resultSet.getDouble("income_amount"));
                inc.setIncome_weekly_interval(databaseConnector.resultSet.getInt("income_weekly_interval"));
                int boolInt = databaseConnector.resultSet.getInt("is_one_time");
                if (boolInt == 1) {
                    inc.setIs_one_time(true);
                } else {
                    inc.setIs_one_time(false);
                }
                inc.setUser_id(user.getUser_id());
                incomes[i] = inc;
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
        return incomes;
    }
}
