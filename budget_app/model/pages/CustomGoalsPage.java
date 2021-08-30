package budget_app.model.pages;

import budget_app.data.CustomGoal;
import budget_app.data.User;
import budget_app.model.Page;
import budget_app.services.DatabaseConnector;
import budget_app.services.SQLDeletes;
import budget_app.services.SQLQueries;
import budget_app.services.SQLUpdates;

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
        SQLDeletes.deleteCustomGoal(this.user);
    }

    private void updateCustomGoal() {
        SQLUpdates.updateCustomGoal(this.user);
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
        CustomGoal[] customGoals = SQLQueries.getCustomGoals(this.user);
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
