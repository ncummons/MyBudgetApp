package budget_app.services;

import budget_app.controllers.UserInterface;
import budget_app.data.*;
import budget_app.model.pages.ExpensePage;

public class SQLUpdates {

        public static void updateAccount(User user){
                DatabaseConnector databaseConnector = new DatabaseConnector();
                int accID;
                System.out.println("To cancel, just type \"0\".");
                System.out.println("Which account would you like to update? Enter the corresponding account id below: ");
                try {
                        accID = UserInterface.takeInputInt();
                        if(accID == 0){
                                return;
                        }
                        databaseConnector.openConnection();
                        String sql = "SELECT accounts.* FROM accounts" +
                                " WHERE accounts.account_id = ?";
                        databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
                        databaseConnector.preparedStatement.setInt(1, accID);
                        databaseConnector.executePreparedSelectStatement();
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
                        String bankName = UserInterface.takeInputString();
                        System.out.println();
                        System.out.print("Account name: ");
                        String accountName = UserInterface.takeInputString();
                        System.out.println();
                        System.out.print("Account balance: ");
                        double accountBalance = UserInterface.takeInputDouble();

                        sql = "UPDATE accounts SET account_name = ?, bank_name = ?, " +
                                "account_balance = ? WHERE accounts.account_id = ?";
                        databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
                        databaseConnector.preparedStatement.setString(1, accountName);
                        databaseConnector.preparedStatement.setString(2, bankName);
                        databaseConnector.preparedStatement.setDouble(3, accountBalance);
                        databaseConnector.preparedStatement.setInt(4, accID);
                        databaseConnector.executePreparedUpdateStatement();
                } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("There was a problem updating your account. Please try again later.");
                }finally {
                        databaseConnector.closeConnection();
                }
        }

        public static void updateCustomGoal(User user){
                DatabaseConnector databaseConnector = new DatabaseConnector();
                int customGoalID;
                System.out.println("To cancel, just type \"0\".");
                System.out.println("Which Goal would you like to update? Enter the corresponding goal id below: ");
                try {
                        customGoalID = UserInterface.takeInputInt();
                        if(customGoalID == 0){
                                return;
                        }
                        databaseConnector.openConnection();
                        String sql = "SELECT custom_goals.* FROM custom_goals " +
                                "WHERE custom_goals.custom_goal_id = ?";
                        databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
                        databaseConnector.preparedStatement.setInt(1, customGoalID);
                        databaseConnector.executePreparedSelectStatement();
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
                        String goalName = UserInterface.takeInputString();
                        System.out.println();
                        System.out.print("Total amount needed: $");
                        double totalNeeded  = UserInterface.takeInputDouble();
                        System.out.println();
                        System.out.print("Monthly contribution amount: $");
                        double monthlyContribution = 0;
                        boolean unconfirmed = true;
                        while(unconfirmed) {
                                monthlyContribution = UserInterface.takeInputDouble();
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
                                amountSaved = UserInterface.takeInputDouble();
                                if(amountSaved >= 0 && amountSaved <= totalNeeded){
                                        unconfirmed = false;
                                } else {
                                        System.out.println("Please choose a valid number between 0 and the total amount needed.");
                                }
                        }
                        System.out.println();

                        sql = "UPDATE custom_goals " +
                                "SET goal_name = ?, total_needed = ?, monthly_contribution = ?, " +
                                "amount_saved = ? WHERE custom_goals.custom_goal_id = ?";
                        databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
                        databaseConnector.preparedStatement.setString(1, goalName);
                        databaseConnector.preparedStatement.setDouble(2, totalNeeded);
                        databaseConnector.preparedStatement.setDouble(3, monthlyContribution);
                        databaseConnector.preparedStatement.setDouble(4, amountSaved);
                        databaseConnector.preparedStatement.setInt(5,customGoalID);
                        databaseConnector.executePreparedUpdateStatement();
                } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("There was a problem updating your goal. Please try again later.");
                }finally {
                        databaseConnector.closeConnection();
                }
        }

        public static void updateDebt(User user){
                DatabaseConnector databaseConnector = new DatabaseConnector();
                int debtID;
                System.out.println("To cancel, just type \"0\".");
                System.out.println("Which Debt would you like to update? Enter the corresponding debt id below: ");
                try {
                        debtID = UserInterface.takeInputInt();
                        if(debtID == 0){
                                return;
                        }
                        databaseConnector.openConnection();
                        String sql = "SELECT debts.* FROM debts " +
                                "WHERE debts.debt_id = ?";
                        databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
                        databaseConnector.preparedStatement.setInt(1, debtID);
                        databaseConnector.executePreparedSelectStatement();
                        if(databaseConnector.resultSet == null){
                                System.out.println("The debt does not exist. Please try again.");
                                return;
                        }
                        // Create the object of the account
                        databaseConnector.resultSet.next();
                        Debt debt = new Debt();
                        debt.setUser_id(user.getUser_id());
                        debt.setDebt_id(databaseConnector.resultSet.getInt("debt_id"));
                        debt.setDebt_name(databaseConnector.resultSet.getString("debt_name"));
                        debt.setMonthly_payment(databaseConnector.resultSet.getDouble("monthly_payment"));
                        debt.setAmount(databaseConnector.resultSet.getDouble("amount"));
                        debt.setInterest_rate(databaseConnector.resultSet.getDouble("interest_rate"));
                        debt.setLender_name(databaseConnector.resultSet.getString("lender_name"));
                        debt.setDebt_due_date(databaseConnector.resultSet.getInt("debt_due_date"));

                        // Take user input for what to update, then update it
                        System.out.print("Debt name: ");
                        String debtName = UserInterface.takeInputString();
                        System.out.println();
                        System.out.print("Debt amount: $");
                        double debtAmount  = UserInterface.takeInputDouble();
                        System.out.println();
                        System.out.print("Monthly payment amount: $");
                        double monthlyPayment  = UserInterface.takeInputDouble();
                        System.out.println();
                        System.out.print("Debt Interest Rate (as %): ");
                        double debtInterestRate = 0;
                        boolean unconfirmed = true;
                        while(unconfirmed) {
                                debtInterestRate = UserInterface.takeInputDouble();
                                if(debtInterestRate >= 0 && debtInterestRate <= 100){
                                        unconfirmed = false;
                                } else {
                                        System.out.println("Please choose a valid percentage between 0 and 100.");
                                }
                        }
                        System.out.println();
                        System.out.print("Lender Name: ");
                        String lenderName = UserInterface.takeInputString();
                        System.out.println();
                        System.out.print("Day of the month that the debt is due: ");
                        int debtDueDate = 1;
                        unconfirmed = true;
                        while(unconfirmed) {
                                debtDueDate = UserInterface.takeInputInt();
                                if(debtDueDate > 0 && debtDueDate < 31){
                                        unconfirmed = false;
                                } else {
                                        System.out.println("Please choose a valid date between 1 and 30.");
                                }
                        }

                        sql = "UPDATE debts SET debt_name = ?, amount = ?, " +
                                "interest_rate = ?, monthly_payment = ?, " +
                                "debt_due_date = ?, lender_name = ? WHERE debts.debt_id = ?";
                        databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
                        databaseConnector.preparedStatement.setString(1, debtName);
                        databaseConnector.preparedStatement.setDouble(2, debtAmount);
                        databaseConnector.preparedStatement.setDouble(3, debtInterestRate);
                        databaseConnector.preparedStatement.setDouble(4, monthlyPayment);
                        databaseConnector.preparedStatement.setInt(5, debtDueDate);
                        databaseConnector.preparedStatement.setString(6,lenderName);
                        databaseConnector.preparedStatement.setInt(7, debtID);
                        databaseConnector.executePreparedUpdateStatement();
                } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("There was a problem updating your debt. Please try again later.");
                }finally {
                        databaseConnector.closeConnection();
                }
        }

        public static void updateExpense(User user){
                DatabaseConnector databaseConnector = new DatabaseConnector();
                int expenseID;
                System.out.println("To cancel, just type \"0\".");
                System.out.println("Which Expense would you like to update? Enter the corresponding expense id below: ");
                try {
                        expenseID = UserInterface.takeInputInt();
                        if (expenseID == 0){
                                return;
                        }
                        databaseConnector.openConnection();
                        String sql = "SELECT expenses.* FROM expenses" +
                                " INNER JOIN users" +
                                " ON users.user_id = expenses.user_id " +
                                "WHERE expenses.expense_id = ?";
                        databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
                        databaseConnector.preparedStatement.setInt(1, expenseID);
                        databaseConnector.executePreparedSelectStatement();

                        if(databaseConnector.resultSet == null){
                                System.out.println("The expense does not exist. Please try again.");
                                return;
                        }
                        // Create the object of the account
                        databaseConnector.resultSet.next();
                        Expense expense = new Expense();
                        expense.setUser_id(user.getUser_id());
                        expense.setExpense_id(databaseConnector.resultSet.getInt("expense_id"));
                        expense.setExpense_name(databaseConnector.resultSet.getString("expense_name"));
                        expense.setExpense_amount(databaseConnector.resultSet.getDouble("expense_amount"));
                        expense.setExpense_category(databaseConnector.resultSet.getString("expense_category"));

                        // Take user input for what to update, then update it
                        System.out.print("Expense name: ");
                        String expenseName = UserInterface.takeInputString();
                        System.out.println();
                        System.out.print("Expense amount: $");
                        double expenseAmount  = UserInterface.takeInputDouble();
                        System.out.println();
                        Expense.Categories cat = ExpensePage.getCategoryFromUser();
                        String categoryString = Expense.getStringFromCategory(cat);


                        sql = "UPDATE expenses SET expense_name = ?, expense_amount = ?, " +
                                "expense_category = ? WHERE expenses.expense_id = ?";
                        databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
                        databaseConnector.preparedStatement.setString(1, expenseName);
                        databaseConnector.preparedStatement.setDouble(2, expenseAmount);
                        databaseConnector.preparedStatement.setString(3, categoryString);
                        databaseConnector.preparedStatement.setInt(4, expenseID);
                        databaseConnector.executePreparedUpdateStatement();

                } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("There was a problem updating your expense. Please try again later.");
                }finally {
                        databaseConnector.closeConnection();
                }
        }

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
