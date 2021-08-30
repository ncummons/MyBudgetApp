package budget_app.services;

import budget_app.controllers.UserInterface;
import budget_app.data.Income;
import budget_app.data.User;

public class SQLUpdates {

        public static void updateIncome(User user){
                DatabaseConnector databaseConnector = new DatabaseConnector();
                int incID;
                System.out.println("Which Income would you like to update? Enter the corresponding income id below: ");
                System.out.println("To cancel, just type \"0\".");
                try {
                        incID = UserInterface.takeInputInt();
                        if(incID == 0){
                                return;
                        }
                        String sql = "SELECT income.* FROM income WHERE income.income_id = ?";
                        databaseConnector.openConnection();
                        databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
                        databaseConnector.preparedStatement.setInt(1, incID);
                        databaseConnector.executePreparedSelectStatement();
                        if(databaseConnector.resultSet == null){
                                System.out.println("The income does not exist. Please try again.");
                                return;
                        }
                        // Create the object of the account
                        databaseConnector.resultSet.next();
                        Income inc = new Income();
                        inc.setUser_id(user.getUser_id());
                        inc.setIncome_id(databaseConnector.resultSet.getInt("income_id"));
                        inc.setIncome_amount(databaseConnector.resultSet.getDouble("income_amount"));
                        inc.setIncome_source(databaseConnector.resultSet.getString("income_source"));
                        inc.setIncome_per_month(databaseConnector.resultSet.getInt("income_per_month"));
                        int boolInt = databaseConnector.resultSet.getInt("is_one_time");
                        if(boolInt == 1) {
                                inc.setIs_one_time(true);
                        }else{
                                inc.setIs_one_time(false);
                        }

                        // Take user input for what to update, then update it
                        System.out.println("Income source: ");
                        String incomeSource = UserInterface.takeInputString();
                        System.out.println();
                        System.out.print("Income amount: $");
                        double incAmount  = UserInterface.takeInputDouble();
                        System.out.println();
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
                        System.out.println();
                        System.out.print("How many times per month do you receive this paycheck? | ");
                        int income_per_month = UserInterface.takeInputInt();

                        sql = "UPDATE income SET income_source = ?, income_amount = ?, is_one_time = ?, " +
                                "income_per_month = ? WHERE income.income_id = ?";
                        databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
                        databaseConnector.preparedStatement.setString(1,incomeSource);
                        databaseConnector.preparedStatement.setDouble(2,incAmount);
                        databaseConnector.preparedStatement.setInt(3,incOT);
                        databaseConnector.preparedStatement.setInt(4,income_per_month);
                        databaseConnector.preparedStatement.setInt(5,incID);
                        databaseConnector.executePreparedUpdateStatement();
                } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("There was a problem updating your income. Please try again later.");
                }finally {
                        databaseConnector.closeConnection();
                }
        }
}
