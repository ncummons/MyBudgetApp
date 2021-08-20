package budget_app.model.pages;

import budget_app.data.User;
import budget_app.model.Page;
import budget_app.services.DatabaseConnector;


public class UserLoginScreen extends Page {

    User user = null;

    public UserLoginScreen() {
    }

    @Override
    public void printPage(){
        System.out.println("Welcome to Budget Central. Please enter your username followed by your password.");
        System.out.println("If you need to create a new username and password, please type: \"new\"");
    }
    @Override
    public Page pageOperations() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        printPage();
        try {
            System.out.print("Username: ");
            System.out.println();
            String username = takeUserInputString();
            if (username.compareToIgnoreCase("new") == 0) {
                return new NewUserPage();
            }
            System.out.print("Password: ");
            System.out.println();
            String password = takeUserInputString();
            try {
                databaseConnector.openConnection();
                String sql = "SELECT * FROM users WHERE users.username = \'" + username + "\';";
                databaseConnector.resultSet = databaseConnector.executeQuery(sql);
                while (databaseConnector.resultSet.next()) {
                    user = new User();
                    user.setUser_id(databaseConnector.resultSet.getInt("user_id"));
                    user.setFirst_name(databaseConnector.resultSet.getString("first_name"));
                    user.setLast_name(databaseConnector.resultSet.getString("last_name"));
                    user.setUsername(databaseConnector.resultSet.getString("username"));
                    user.setPassword(databaseConnector.resultSet.getString("password"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                if (password.compareTo(user.getPassword()) == 0) {
                    System.out.println("Username and password valid.");
                    databaseConnector.closeConnection();
                    return new MainMenu(user);
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                    return pageOperations();
                }
            } catch (NullPointerException e){
                System.out.println("Invalid username or password. Please try again.");
                return pageOperations();
            }
        } catch (Exception e){
            e.printStackTrace();
            return pageOperations();
        } finally {
            databaseConnector.closeConnection();
        }
    }


}
