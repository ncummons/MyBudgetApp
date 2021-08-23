package budget_app.model.pages;

import budget_app.data.CustomGoal;
import budget_app.data.Debt;
import budget_app.data.User;
import budget_app.model.Page;
import budget_app.services.DatabaseConnector;

import java.sql.SQLException;

public class CustomGoalsPage extends Page {
    User user;

    public CustomGoalsPage(User user) {
        this.user = user;
    }

    @Override
    public Page pageOperations() {
        printPage();
        CustomGoal[] customGoals = getCustomGoals();
        printCustomGoalInfo(customGoals);
        int input = takeUserInputInt();
        switch (input) {
            case 1:
                return new MainMenu(user);

            case 2:
                return null;

            case 3:
                addCustomGoals();
                return pageOperations();

            case 4:
                updateCustomGoal();
                return pageOperations();

            case 5:
                deleteCustomGoal();
                return pageOperations();

            default:
                System.out.println("Invalid input, try again.");
                return pageOperations();
        }
    }

    private void deleteCustomGoal() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        int customGoalID;
        System.out.println("Which goal would you like to delete? Enter the corresponding goal id below.");
        try {
            customGoalID = takeUserInputInt();
            String sql = "DELETE FROM custom_goals WHERE custom_goal_id = " + customGoalID + ";";
            databaseConnector.openConnection();
            databaseConnector.executeDeleteStatement(sql);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
    }

    private void updateCustomGoal() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        int customGoalID;
        System.out.println("Which Goal would you like to update? Enter the corresponding goal id below: ");
        try {
            customGoalID = takeUserInputInt();
            databaseConnector.openConnection();
            databaseConnector.resultSet = databaseConnector.executeQuery("SELECT custom_goals.* FROM custom_goals " +
                    "WHERE custom_goals.custom_goal_id = " + customGoalID + ";");

            if(databaseConnector.resultSet == null){
                System.out.println("The goal does not exist. Please try again.");
                return;
            }
            // Create the object of the account
            databaseConnector.resultSet.next();
            CustomGoal customGoal = new CustomGoal();
            customGoal.setUser_id(user.getUser_id());
            customGoal.setCustom_goal_id(databaseConnector.resultSet.getInt("custom_goal_id"));
            customGoal.setGoal_name(databaseConnector.resultSet.getString("goal_name"));
            customGoal.setMonthly_contribution(databaseConnector.resultSet.getDouble("monthly_contribution"));
            customGoal.setTotal_needed(databaseConnector.resultSet.getDouble("total_needed"));
            customGoal.setAmount_saved(databaseConnector.resultSet.getDouble("amount_saved"));


            // Take user input for what to update, then update it
            System.out.print("Goal name: ");
            String goalName = takeUserInputString();
            System.out.println();
            System.out.print("Total amount needed: $");
            double totalNeeded  = takeUserInputDouble();
            System.out.println();
            System.out.print("Monthly contribution amount: $");
            double monthlyContribution = 0;
            boolean unconfirmed = true;
            while(unconfirmed) {
                monthlyContribution = takeUserInputDouble();
                if(monthlyContribution >= 0 && monthlyContribution <= totalNeeded){
                    unconfirmed = false;
                } else {
                    System.out.println("Please choose a valid number between 0 and the total amount needed.");
                }
            }
            System.out.println();
            System.out.print("Amount saved so far: $");
            double amountSaved = 0;
            unconfirmed = true;
            while(unconfirmed) {
                amountSaved = takeUserInputDouble();
                if(amountSaved >= 0 && amountSaved <= totalNeeded){
                    unconfirmed = false;
                } else {
                    System.out.println("Please choose a valid number between 0 and the total amount needed.");
                }
            }
            System.out.println();

            databaseConnector.executeUpdateStatement("UPDATE custom_goals " +
                    "SET goal_name = \"" + goalName + "\", total_needed = " + totalNeeded + ", monthly_contribution = " +
                    monthlyContribution + ", amount_saved = " + amountSaved + " WHERE custom_goals.custom_goal_id = " + customGoalID + ";");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem updating your goal. Please try again later.");
        }finally {
            databaseConnector.closeConnection();
        }
    }

    private void addCustomGoals() {
        System.out.println("How many Goals would you like to add?");
        int numGoals = takeUserInputInt();
        DatabaseConnector databaseConnector = new DatabaseConnector();
        for(int i = 0; i < numGoals; i++) {
            try {
                // take user input, prepare the sql insert statement, and execute the statement to insert the account
                System.out.println("To cancel, type \"cancel\" into Goal name.");
                System.out.println("Goal name: ");
                String goalName = takeUserInputString();
                if(goalName.compareToIgnoreCase("cancel") == 0){
                    i = numGoals;
                    return;
                }
                System.out.print("Total amount needed to save: $");
                double totalNeeded = takeUserInputDouble();
                System.out.println();
                System.out.print("Monthly contribution amount: $");
                double monthlyContribution = 0;
                boolean unconfirmed = true;
                while(unconfirmed) {
                    monthlyContribution = takeUserInputDouble();
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
                    amountSaved = takeUserInputDouble();
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

                databaseConnector.preparedStatement = databaseConnector.prepareInsertStatement(sqlInsert);
                databaseConnector.preparedStatement.setString(1, goalName);
                databaseConnector.preparedStatement.setDouble(2, totalNeeded);
                databaseConnector.preparedStatement.setDouble(3,monthlyContribution);
                databaseConnector.preparedStatement.setDouble(4,amountSaved);
                databaseConnector.preparedStatement.setInt(5, user.getUser_id());

                databaseConnector.executePreparedStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnector.closeConnection();
            }
        }
    }

    private void printCustomGoalInfo(CustomGoal[] customGoals) {
        for (CustomGoal c: customGoals) {
            int monthsToGoal = (int)Math.ceil((c.getTotal_needed() - c.getAmount_saved()) / c.getMonthly_contribution());
            double amountLeft = c.getTotal_needed() - c.getAmount_saved();
            System.out.print("Goal ID: " + c.getCustom_goal_id() + " | Goal Name: " + c.getGoal_name() + " |");
            System.out.println();
            System.out.println("Total Amount Needed: $" + c.getTotal_needed() +
                    " | Monthly Contribution: $" + c.getMonthly_contribution() +
                    " | Amount saved: $" + c.getAmount_saved() + "\n" +
                    "Amount left to save: $" + amountLeft +
                    " | Months until savings goal accomplished: " + monthsToGoal + " months |");
            System.out.println("______________________________________________________________________________");
        }
    }

    private CustomGoal[] getCustomGoals() {
        CustomGoal[] customGoals = null;
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {

            // Get the number of expenses from the query, to dynamically increase or decrease the size of the array
            databaseConnector.openConnection();
            databaseConnector.resultSet = databaseConnector.executeQuery("SELECT COUNT(custom_goals.custom_goal_id) FROM custom_goals" +
                    " INNER JOIN users" +
                    " ON users.user_id = custom_goals.user_id " +
                    "WHERE users.user_id = " + user.getUser_id() + ";");
            databaseConnector.resultSet.next();
            int numGoals = databaseConnector.resultSet.getInt("COUNT(custom_goals.custom_goal_id)");
            databaseConnector.closeConnection();

            // Query the accounts and map them to account objects, then populate array

            customGoals = new CustomGoal[numGoals]; // Accounts Array to be returned

            databaseConnector.openConnection();
            databaseConnector.resultSet = databaseConnector.executeQuery("SELECT custom_goals.* FROM custom_goals" +
                    " INNER JOIN users" +
                    " ON users.user_id = custom_goals.user_id " +
                    "WHERE users.user_id = " + user.getUser_id() + ";");
            int i = 0;
            while (databaseConnector.resultSet.next()) {
                CustomGoal customGoal = new CustomGoal();
                customGoal.setCustom_goal_id(databaseConnector.resultSet.getInt("custom_goal_id"));
                customGoal.setGoal_name(databaseConnector.resultSet.getString("goal_name"));
                customGoal.setTotal_needed(databaseConnector.resultSet.getDouble("total_needed"));
                customGoal.setMonthly_contribution(databaseConnector.resultSet.getDouble("monthly_contribution"));
                customGoal.setAmount_saved(databaseConnector.resultSet.getDouble("amount_saved"));
                customGoal.setUser_id(user.getUser_id());
                customGoals[i] = customGoal;
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
        return customGoals;
    }

    @Override
    public void printPage(){
        System.out.println("______________________________________________________________________________");
        System.out.println("Welcome to the Custom Goals Page, where you can set and track savings goals! \n" +
                "You'll find your goal information below.");
        System.out.println();
        System.out.println("When you would like to move back to the main menu, simply type \"1\".");
        System.out.println("If you would like to close out of the program,simply type \"2\".");
        System.out.println("If you would like to add a new custom goal, simply type \"3\".");
        System.out.println("If you would like to update custom goal information, simply type \"4\".");
        System.out.println("If you would like to delete a custom goal, simply type \"5\".");
        System.out.println("______________________________________________________________________________");
            }
}
