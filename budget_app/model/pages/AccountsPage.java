package budget_app.model.pages;

import budget_app.data.Account;
import budget_app.data.User;
import budget_app.model.Page;
import budget_app.services.DatabaseConnector;


import java.sql.SQLException;

public class AccountsPage extends Page {

    User user;

    public AccountsPage(User user) {
        this.user = user;
    }

    @Override
    public void printPage() {
        System.out.println("______________________________________________________________________________");
        System.out.println("Welcome to the accounts page! You'll find your account information below.");
        System.out.println();
        System.out.println("When you would like to move back to the main menu, simply type \"1\".");
        System.out.println("If you would like to close out of the program,simply type \"2\".");
        System.out.println("If you would like to add account(s), simply type \"3\".");
        System.out.println("If you would like to update account information, simply type \"4\".");
        System.out.println("If you would like to delete an account, simply type \"5\".");
        System.out.println("______________________________________________________________________________");
    }

    // Still need to add functionality to update accounts

    @Override
    public Page pageOperations() {
        printPage();
        Account[] accounts = getAccounts();
        printAccountInfo(accounts);
        int input = takeUserInputInt();
        switch (input){
            case 1: return new MainMenu(user);

            case 2: return null;

            case 3: addAccounts(); return pageOperations();

            case 4: updateAccount(); return pageOperations();

            case 5: deleteAccount(); return pageOperations();

            default:
                System.out.println("Invalid input, try again.");
                return pageOperations();
        }
    }

    private void deleteAccount() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        int accID;
        System.out.println("Which account would you like to delete? Enter the corresponding account id below.");
        try {
            accID = takeUserInputInt();
            String sql = "DELETE FROM ACCOUNTS WHERE account_id = " + accID + ";";
            databaseConnector.openConnection();
            databaseConnector.executeDeleteStatement(sql);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
    }


    private void updateAccount() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        int accID;
        System.out.println("Which account would you like to update? Enter the corresponding account id below: ");
        try {
            accID = takeUserInputInt();
            databaseConnector.openConnection();
            databaseConnector.resultSet = databaseConnector.executeQuery("SELECT accounts.* FROM accounts" +
                    " INNER JOIN users" +
                    " ON users.user_id = accounts.user_id " +
                    "WHERE accounts.account_id = " + accID + ";");
            if(databaseConnector.resultSet == null){
                System.out.println("The account does not exist. Please try again.");
                return;
            }
            // Create the object of the account
            databaseConnector.resultSet.next();
            Account acc = new Account();
            acc.setUser_id(user.getUser_id());
            acc.setAccount_id(databaseConnector.resultSet.getInt("account_id"));
            acc.setAccount_name(databaseConnector.resultSet.getString("account_name"));
            acc.setBank_name(databaseConnector.resultSet.getString("bank_name"));
            acc.setAccount_balance(databaseConnector.resultSet.getDouble("account_balance"));

            // Take user input for what to update, then update it
            System.out.print("Bank name: ");
            String bankName = takeUserInputString();
            System.out.println();
            System.out.print("Account name: ");
            String accountName = takeUserInputString();
            System.out.println();
            System.out.print("Account balance: ");
            double accountBalance = takeUserInputDouble();

            int rows = databaseConnector.executeUpdateStatement("UPDATE accounts " +
                    "SET account_name = \"" + accountName + "\", bank_name = \"" + bankName + "\", " +
                    "account_balance = " + accountBalance + " WHERE accounts.account_id = " + accID + ";");
            if(rows != 0){
                System.out.println("Update successful.");
            } else {
                System.out.println("There was a problem updating your account. Please try again later.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem updating your account. Please try again later.");
        }finally {
            databaseConnector.closeConnection();
        }
    }

    // Uses a loop that takes in an int of user input to determine how many accounts
    // they would like to add

    // Allows users to add accounts and inserts into database

    private void addAccounts(){
        System.out.println("How many accounts would you like to add?");
        int numAccs = takeUserInputInt();
        DatabaseConnector databaseConnector = new DatabaseConnector();
        for(int i = 0; i < numAccs; i++) {
            try {
                // take user input, prepare the sql insert statement, and execute the statement to insert the account
                System.out.println("To cancel, type \"cancel\" into account name");
                System.out.println("Account Name: ");
                String accountName = takeUserInputString();
                if(accountName.compareToIgnoreCase("cancel") == 0){
                    i = numAccs;
                    return;
                }
                System.out.println("Bank Name: ");
                String bankName = takeUserInputString();
                System.out.println("Account Balance: ");
                System.out.print("$");
                double accountBalance = takeUserInputDouble();

                // INSERT INTO orders ( userid, timestamp)
                // SELECT o.userid , o.timestamp FROM users u INNER JOIN orders o ON  o.userid = u.id

                String sqlInsert = "INSERT INTO accounts(account_name, bank_name, account_balance, user_id) VALUES(?, ?, ?, ?)";

                databaseConnector.openConnection();

                databaseConnector.preparedStatement = databaseConnector.prepareInsertStatement(sqlInsert);
                databaseConnector.preparedStatement.setString(1, accountName);
                databaseConnector.preparedStatement.setString(2, bankName);
                databaseConnector.preparedStatement.setDouble(3, accountBalance);
                databaseConnector.preparedStatement.setInt(4, user.getUser_id());

                databaseConnector.executePreparedStatement();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnector.closeConnection();
            }
        }
    }

    // Pull the user accounts from the data into an array of Accounts

    private Account[] getAccounts(){
        Account[] accounts = null;
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try{

            // Get the number of accounts from the query, to dynamically increase or decrease the size of the array
            databaseConnector.openConnection();
            databaseConnector.resultSet = databaseConnector.executeQuery("SELECT COUNT(accounts.account_id) FROM accounts" +
                    " INNER JOIN users" +
                    " ON users.user_id = accounts.user_id " +
                    "WHERE users.user_id = " + user.getUser_id() + ";");
            databaseConnector.resultSet.next();
            int numAccounts = databaseConnector.resultSet.getInt("COUNT(accounts.account_id)");
            databaseConnector.closeConnection();

            // Query the accounts and map them to account objects, then populate array

            accounts = new Account[numAccounts]; // Accounts Array to be returned

            databaseConnector.openConnection();
            databaseConnector.resultSet = databaseConnector.executeQuery("SELECT accounts.* FROM accounts" +
                    " INNER JOIN users" +
                    " ON users.user_id = accounts.user_id " +
                    "WHERE users.user_id = " + user.getUser_id() + ";");
            int i = 0;
            while(databaseConnector.resultSet.next()){
                Account acc = new Account();
                acc.setAccount_id(databaseConnector.resultSet.getInt("account_id"));
                acc.setAccount_name(databaseConnector.resultSet.getString("account_name"));
                acc.setBank_name(databaseConnector.resultSet.getString("bank_name"));
                acc.setAccount_balance(databaseConnector.resultSet.getDouble("account_balance"));
                accounts[i] = acc;
                i++;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
        return accounts;
    }

    // Prints out the user account information from the array of accounts

    private void printAccountInfo(Account[] accounts){
        for (Account a: accounts) {
            System.out.print("Account ID: " + a.getAccount_id() + " | Bank: " + a.getBank_name() +
                    " | " + "Account name: " + a.getAccount_name());
            System.out.println();
            System.out.println("Account Balance: $" + a.getAccount_balance());
            System.out.println("______________________________________________________________________________");
        }
    }

}
