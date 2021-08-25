package budget_app.model.pages;

import budget_app.data.User;
import budget_app.model.Page;
import budget_app.services.DatabaseConnector;

public class UserInformationPage extends Page {
    User user;

    public UserInformationPage(User user) {
        this.user = user;
    }

    @Override
    public void printPage() {
        System.out.println("______________________________________________________________________________");
        System.out.println("Welcome to the User Information, where you can see and edit your user information!");
        System.out.println();
        System.out.println("When you would like to move back to the main menu, simply type \"1\".");
        System.out.println("If you would like to close out of the program,simply type \"2\".");
        System.out.println("If you would like to change your username, simply type \"3\".");
        System.out.println("If you would like to change your password, simply type \"4\".");
        System.out.println("If you would like to update your first name, simply type \"5\".");
        System.out.println("If you would like to update your last name, simpy type \"6\".");
        System.out.println("______________________________________________________________________________");
    }

    @Override
    public Page pageOperations() {
        printPage();
        printUserInfo();
        int input = takeUserInputInt();
        switch (input) {
            case 1:
                return new MainMenu(user);

            case 2:
                return null;

            case 3:
                changeUsername();
                return pageOperations();

            case 4:
                changePassword();
                return pageOperations();

            case 5:
                changeFirstName();
                return pageOperations();
            case 6:
                changeLastName();
                return pageOperations();

            default:
                System.out.println("Invalid input, try again.");
                return pageOperations();
    }
}

    private void changeLastName() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        System.out.print("Last Name: ");
        String lastName = takeUserInputString();
        System.out.println();

        try {
            String pStatement = "UPDATE users set last_name = ? WHERE user_id = ?";
            databaseConnector.openConnection();
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(pStatement);
            databaseConnector.preparedStatement.setString(1, lastName);
            databaseConnector.preparedStatement.setInt(2,user.getUser_id());
            int x = databaseConnector.executePreparedUpdateStatement();
            if(x == 1){
                System.out.println("Last name updated to: " + lastName + ". Thanks!");
            }
        } catch (Exception e){
            System.out.println("There was a problem updating your user information.");
        } finally {
            databaseConnector.closeConnection();
        }
    }

    private void changeFirstName() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        System.out.print("First Name: ");
        String firstName = takeUserInputString();
        System.out.println();

        try {
            String pStatement = "UPDATE users set first_name = ? WHERE user_id = ?";
            databaseConnector.openConnection();
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(pStatement);
            databaseConnector.preparedStatement.setString(1, firstName);
            databaseConnector.preparedStatement.setInt(2,user.getUser_id());
            int x = databaseConnector.executePreparedUpdateStatement();
            if(x == 1){
                System.out.println("First name updated to: " + firstName + ". Thanks!");
            }
        } catch (Exception e){
            System.out.println("There was a problem updating your user information.");
        } finally {
            databaseConnector.closeConnection();
        }
    }

    private void changePassword() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        boolean unconfirmed = true;
        String password = user.getPassword();
        while(unconfirmed) {
            System.out.print("New Password: ");
            password = takeUserInputString();
            System.out.println();
            System.out.print("Please confirm password: ");
            String confirmation = takeUserInputString();
            System.out.println();
            if (password.compareTo(confirmation) == 0) {
                unconfirmed = false;
            } else {
                unconfirmed = true;
                System.out.println("The passwords did not match. Please try again.");
            }
        }
        try {
            String pStatement = "UPDATE users set password = ? WHERE user_id = ?";
            databaseConnector.openConnection();
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(pStatement);
            databaseConnector.preparedStatement.setString(1, password);
            databaseConnector.preparedStatement.setInt(2,user.getUser_id());
            int x = databaseConnector.executePreparedUpdateStatement();
            if(x == 1){
                System.out.println("Password updated. Thanks!");
            }
        } catch (Exception e){
            System.out.println("There was a problem updating your user information.");
        } finally {
            databaseConnector.closeConnection();
        }
    }

    private void changeUsername() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        System.out.print("New Username: ");
        String username = takeUserInputString();
        System.out.println();

        try {
            String pStatement = "UPDATE users set username = ? WHERE user_id = ?";
            databaseConnector.openConnection();
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(pStatement);
            databaseConnector.preparedStatement.setString(1, username);
            databaseConnector.preparedStatement.setInt(2,user.getUser_id());
            int x = databaseConnector.executePreparedUpdateStatement();
            if(x == 1){
                System.out.println("Username updated to: " + username + ". Thanks!");
            }
        } catch (Exception e){
            System.out.println("There was a problem updating your user information.");
        } finally {
            databaseConnector.closeConnection();
        }
    }


    private void printUserInfo() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {
            String pStatement = "SELECT * FROM users WHERE user_id = ?";
            databaseConnector.openConnection();
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(pStatement);
            databaseConnector.preparedStatement.setInt(1, user.getUser_id());
            databaseConnector.executePreparedSelectStatement();
            databaseConnector.resultSet.next();
            String username = databaseConnector.resultSet.getString("username");
            String password = databaseConnector.resultSet.getString("password");
            String firstName = databaseConnector.resultSet.getString("first_name");
            String lastName = databaseConnector.resultSet.getString("last_name");
            System.out.println("Username: " + username + " | Password: " + password);
            System.out.println("First name: " + firstName + " | Last name: " + lastName);
            System.out.println("___________________________________________________________________");
        } catch (Exception e){
            System.out.println("There was a problem accessing your user information.");
        } finally {
            databaseConnector.closeConnection();
        }
    }
}
