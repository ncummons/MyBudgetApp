package budget_app.services;

import budget_app.data.User;
import budget_app.controllers.UserInterface;

import java.sql.SQLException;

public class SQLInserts {

    public static void addAccounts(User user){

    }

    public static void addCustomGoals(User user){

    }

    public static void addDebts(User user){

    }

    public static void addExpenses(User user){

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
