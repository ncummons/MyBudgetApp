package budget_app.services;

import budget_app.controllers.UserInterface;
import budget_app.data.User;

public class SQLDeletes {

    public static void deleteAccount(User user){
        DatabaseConnector databaseConnector = new DatabaseConnector();
        int accID;
        System.out.println("To cancel, just type \"0\".");
        System.out.println("Which account would you like to delete? Enter the corresponding account id below.");
        try {
            accID = UserInterface.takeInputInt();
            if(accID ==0){
                return;
            }
            databaseConnector.openConnection();
            String sql = "DELETE FROM ACCOUNTS WHERE account_id = ?";
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
            databaseConnector.preparedStatement.setInt(1,accID);
            databaseConnector.executePreparedDeleteStatement();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
    }

    public static void deleteCustomGoal(User user){
        DatabaseConnector databaseConnector = new DatabaseConnector();
        int customGoalID;
        System.out.println("To cancel, just type \"0\".");
        System.out.println("Which goal would you like to delete? Enter the corresponding goal id below.");
        try {
            customGoalID = UserInterface.takeInputInt();
            if(customGoalID == 0){
                return;
            }
            String sql = "DELETE FROM custom_goals WHERE custom_goal_id = ?";
            databaseConnector.openConnection();
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
            databaseConnector.preparedStatement.setInt(1, customGoalID);
            databaseConnector.executePreparedDeleteStatement();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
    }

    public static void deleteDebt(User user){
        DatabaseConnector databaseConnector = new DatabaseConnector();
        int debtID;
        System.out.println("To cancel, just type \"0\".");
        System.out.println("Which Debt would you like to delete? Enter the corresponding debt id below.");
        try {
            debtID = UserInterface.takeInputInt();
            if(debtID == 0){
                return;
            }
            String sql = "DELETE FROM debts WHERE debt_id = ?";
            databaseConnector.openConnection();
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
            databaseConnector.preparedStatement.setInt(1, debtID);
            databaseConnector.executePreparedDeleteStatement();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
    }

    public static void deleteExpense(User user){
        DatabaseConnector databaseConnector = new DatabaseConnector();
        int expenseID;
        System.out.println("To cancel, just type \"0\".");
        System.out.println("Which Expense would you like to delete? Enter the corresponding expense id below.");
        try {
            expenseID = UserInterface.takeInputInt();
            if(expenseID == 0){
                return;
            }
            String sql = "DELETE FROM expenses WHERE expense_id = ?";
            databaseConnector.openConnection();
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
            databaseConnector.preparedStatement.setInt(1,expenseID);
            databaseConnector.executePreparedDeleteStatement();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
    }

    public static void deleteIncome(User user){
        DatabaseConnector databaseConnector = new DatabaseConnector();
        int incID;
        System.out.println("Which Income would you like to delete? Enter the corresponding income id below.");
        System.out.println("To cancel, just type \"0\".");
        try {
            incID = UserInterface.takeInputInt();
            if (incID == 0){
                return;
            }
            String sql = "DELETE FROM income WHERE income_id = ?";
            databaseConnector.openConnection();
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
            databaseConnector.preparedStatement.setInt(1, incID);
            databaseConnector.executePreparedDeleteStatement();
        } catch (
                Exception e) {
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }

    }

}
