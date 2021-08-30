package budget_app.model.pages;

import budget_app.data.CustomGoal;
import budget_app.data.User;
import budget_app.model.Page;
import budget_app.services.*;

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
        SQLInserts.addCustomGoals(this.user);
    }

    private void printCustomGoalInfo(CustomGoal[] customGoals) {
        for (CustomGoal c: customGoals) {
            int monthsToGoal = Calculations.customGoalMonthsToGoal(c);
            double amountLeft = Calculations.amountToGoal(c);
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
