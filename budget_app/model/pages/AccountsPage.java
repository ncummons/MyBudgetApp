package budget_app.model.pages;

import budget_app.data.Account;
import budget_app.data.User;
import budget_app.model.Page;
import budget_app.services.DatabaseConnector;
import budget_app.services.SQLDeletes;
import budget_app.services.SQLQueries;
import budget_app.services.SQLUpdates;


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
        SQLDeletes.deleteAccount(this.user);
    }


    private void updateAccount() {
        SQLUpdates.updateAccount(this.user);
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

    // Pull the user accounts from the data into an array of Accounts

    private Account[] getAccounts(){
        Account[] accounts = SQLQueries.getAccounts(this.user);
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
