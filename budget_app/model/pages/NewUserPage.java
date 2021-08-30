package budget_app.model.pages;

import budget_app.data.User;
import budget_app.model.Page;
import budget_app.services.DatabaseConnector;
import budget_app.services.SQLInserts;


public class NewUserPage extends Page {
    User user;

    // Prints out the messages for the page
    @Override
    public void printPage() {
        System.out.println("Thank you for creating a new account with Budget Central.");
        System.out.println("Please enter your first name, last name, username, and password below.");
    }

    // Sets the username variable for the user and prevents duplicates

    private String setUsername(){
        String username;
        System.out.print("Username: ");
        username = takeUserInputString();
        System.out.println();
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try{
            databaseConnector.openConnection();
            databaseConnector.resultSet = databaseConnector.executeQuery("SELECT COUNT(*) " +
                    "FROM users where users.username = \'" + username +"\';");
            databaseConnector.resultSet.next();
            if(databaseConnector.resultSet.getInt("COUNT(*)") != 0) {
                System.out.println("Username is already taken. Please try again.");
                databaseConnector.closeConnection();
                return setUsername();
            } else{
                System.out.println("Username available.");
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
        return username;
    }

    // Sets the password for the user and confirms with the user by having them input it twice
    private void setPassword(String first_name, String last_name, String username){
        boolean unconfirmed = true;
        String password;
        while(unconfirmed) {
            System.out.print("Password: ");
            password = takeUserInputString();
            System.out.println();
            System.out.print("Please confirm password: ");
            String confirmation = takeUserInputString();
            System.out.println();
            if (password.compareTo(confirmation) == 0) {
                this.user = new User(first_name, last_name, username, password);
                unconfirmed = false;
            } else {
                unconfirmed = true;
                System.out.println("The passwords did not match. Please try again.");
            }
        }
    }

    // Inserts the user information into the database
    private void insertUser(){
        SQLInserts.addUser(this.user);
    }

    // Actual operations of the page, this is the "main method" of the page
    @Override
    public Page pageOperations() {
        printPage();
        String username;
        String first_name;
        String last_name;
        System.out.println("First name: ");
        first_name = takeUserInputString();
        System.out.println();
        System.out.println("Last name: ");
        last_name = takeUserInputString();
        System.out.println();
        username = setUsername();
        setPassword(first_name,last_name,username);
        insertUser();
        System.out.println("You have successfully created an account with Budget Central, " + user.getFirst_name() + ".");
        return new MainMenu(user);
    }
}
