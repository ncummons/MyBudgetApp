package budget_app.services;

import budget_app.data.*;

import java.sql.SQLException;

public class SQLQueries {

    public static Account[] getAccounts(User user){
        Account[] accounts = null;
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try{

            // Get the number of accounts from the query, to dynamically increase or decrease the size of the array
            String sql = "SELECT COUNT(accounts.account_id) FROM accounts INNER JOIN users ON users.user_id = accounts.user_id " +
                    "WHERE users.user_id = ?";
            databaseConnector.openConnection();
            //databaseConnector.resultSet = databaseConnector.executeQuery("SELECT COUNT(accounts.account_id) FROM accounts" +
            //        " INNER JOIN users" +
            //        " ON users.user_id = accounts.user_id " +
            //        "WHERE users.user_id = " + user.getUser_id() + ";");
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
            databaseConnector.preparedStatement.setInt(1, user.getUser_id());
            databaseConnector.executePreparedSelectStatement();
            databaseConnector.resultSet.next();
            int numAccounts = databaseConnector.resultSet.getInt("COUNT(accounts.account_id)");
            databaseConnector.closeConnection();

            // Query the accounts and map them to account objects, then populate array

            accounts = new Account[numAccounts]; // Accounts Array to be returned

            databaseConnector.openConnection();
            sql = "SELECT accounts.* FROM accounts INNER JOIN users ON users.user_id = accounts.user_id WHERE " +
                    "users.user_id = ?";
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
            databaseConnector.preparedStatement.setInt(1,user.getUser_id());
            databaseConnector.executePreparedSelectStatement();
            int i = 0;
            while(databaseConnector.resultSet.next()){
                Account acc = new Account();
                acc.setAccount_id(databaseConnector.resultSet.getInt("account_id"));
                acc.setAccount_name(databaseConnector.resultSet.getString("account_name"));
                acc.setBank_name(databaseConnector.resultSet.getString("bank_name"));
                acc.setAccount_balance(databaseConnector.resultSet.getDouble("account_balance"));
                accounts[i] = acc;
                i++;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
        return accounts;
    }

    public static Account getAccount(int account_id){
        Account acc = new Account();
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try{
            String sql = "SELECT * FROM accounts WHERE accounts.account_id = ?";
            databaseConnector.openConnection();
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
            databaseConnector.preparedStatement.setInt(1, account_id);
            databaseConnector.executePreparedSelectStatement();
            acc.setAccount_id(databaseConnector.resultSet.getInt("account_id"));
            acc.setAccount_name(databaseConnector.resultSet.getString("account_name"));
            acc.setBank_name(databaseConnector.resultSet.getString("bank_name"));
            acc.setAccount_balance(databaseConnector.resultSet.getDouble("account_balance"));
            acc.setUser_id(databaseConnector.resultSet.getInt("user_id"));
        } catch (Exception e) {
            System.out.println("There was a problem finding the account.");
        } finally {
            databaseConnector.closeConnection();
        }
        return acc;
    }

    public static CustomGoal[] getCustomGoals(User user){
        CustomGoal[] customGoals = null;
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {

            // Get the number of expenses from the query, to dynamically increase or decrease the size of the array
            databaseConnector.openConnection();
            String sql = "SELECT COUNT(custom_goals.custom_goal_id) FROM custom_goals" +
                    " INNER JOIN users" +
                    " ON users.user_id = custom_goals.user_id " +
                    "WHERE users.user_id = ?";
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
            databaseConnector.preparedStatement.setInt(1, user.getUser_id());
            databaseConnector.executePreparedSelectStatement();
            databaseConnector.resultSet.next();
            int numGoals = databaseConnector.resultSet.getInt("COUNT(custom_goals.custom_goal_id)");
            databaseConnector.closeConnection();

            // Query the accounts and map them to account objects, then populate array

            customGoals = new CustomGoal[numGoals]; // Accounts Array to be returned

            databaseConnector.openConnection();
            sql = "SELECT custom_goals.* FROM custom_goals" +
                    " INNER JOIN users" +
                    " ON users.user_id = custom_goals.user_id " +
                    "WHERE users.user_id = ?";
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
            databaseConnector.preparedStatement.setInt(1,user.getUser_id());
            databaseConnector.executePreparedSelectStatement();
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

    public static Debt[] getDebts(User user){
        Debt[] debts = null;
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {

            // Get the number of expenses from the query, to dynamically increase or decrease the size of the array
            databaseConnector.openConnection();
            String sql = "SELECT COUNT(debts.debt_id) FROM debts" +
                    " INNER JOIN users" +
                    " ON users.user_id = debts.user_id " +
                    "WHERE users.user_id = ?";
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
            databaseConnector.preparedStatement.setInt(1, user.getUser_id());
            databaseConnector.executePreparedSelectStatement();
            databaseConnector.resultSet.next();
            int numDebts = databaseConnector.resultSet.getInt("COUNT(debts.debt_id)");
            databaseConnector.closeConnection();

            // Query the accounts and map them to account objects, then populate array

            debts = new Debt[numDebts]; // Accounts Array to be returned

            databaseConnector.openConnection();
            sql = "SELECT debts.* FROM debts" +
                    " INNER JOIN users" +
                    " ON users.user_id = debts.user_id " +
                    "WHERE users.user_id = ?";
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
            databaseConnector.preparedStatement.setInt(1, user.getUser_id());
            databaseConnector.executePreparedSelectStatement();
            int i = 0;
            while (databaseConnector.resultSet.next()) {
                Debt debt = new Debt();
                debt.setDebt_id(databaseConnector.resultSet.getInt("debt_id"));
                debt.setDebt_name(databaseConnector.resultSet.getString("debt_name"));
                debt.setAmount(databaseConnector.resultSet.getDouble("amount"));
                debt.setMonthly_payment(databaseConnector.resultSet.getDouble("monthly_payment"));
                debt.setInterest_rate(databaseConnector.resultSet.getDouble("interest_rate"));
                debt.setDebt_due_date(databaseConnector.resultSet.getInt("debt_due_date"));
                debt.setLender_name(databaseConnector.resultSet.getString("lender_name"));
                debt.setUser_id(user.getUser_id());
                debts[i] = debt;
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
        return debts;
    }

    public static Debt getDebt(int debt_id){
        Debt debt = new Debt();
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try{
            String sql = "SELECT * FROM debts WHERE debts.debt_id = ?";
            databaseConnector.openConnection();
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
            databaseConnector.preparedStatement.setInt(1, debt_id);
            databaseConnector.executePreparedSelectStatement();
            databaseConnector.resultSet.next();
            debt.setDebt_id(databaseConnector.resultSet.getInt("debt_id"));
            debt.setDebt_name(databaseConnector.resultSet.getString("debt_name"));
            debt.setAmount(databaseConnector.resultSet.getDouble("amount"));
            debt.setMonthly_payment(databaseConnector.resultSet.getDouble("monthly_payment"));
            debt.setInterest_rate(databaseConnector.resultSet.getDouble("interest_rate"));
            debt.setLender_name(databaseConnector.resultSet.getString("lender_name"));
            debt.setDebt_due_date(databaseConnector.resultSet.getInt("debt_due_date"));
            debt.setUser_id(databaseConnector.resultSet.getInt("user_id"));
        } catch (Exception e) {
            System.out.println("There was a problem finding the debt.");
        } finally {
            databaseConnector.closeConnection();
        }
        return debt;
    }

    public static Expense[] getExpenses(User user){
        Expense[] expenses = null;
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {

            // Get the number of expenses from the query, to dynamically increase or decrease the size of the array
            databaseConnector.openConnection();
            String sql = "SELECT COUNT(expenses.expense_id) FROM expenses" +
                    " INNER JOIN users" +
                    " ON users.user_id = expenses.user_id " +
                    "WHERE users.user_id = ?";
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
            databaseConnector.preparedStatement.setInt(1, user.getUser_id());
            databaseConnector.executePreparedSelectStatement();
            databaseConnector.resultSet.next();
            int numExpenses = databaseConnector.resultSet.getInt("COUNT(expenses.expense_id)");
            databaseConnector.closeConnection();

            // Query the accounts and map them to account objects, then populate array

            expenses = new Expense[numExpenses]; // Accounts Array to be returned

            databaseConnector.openConnection();
            sql = "SELECT expenses.* FROM expenses" +
                    " INNER JOIN users" +
                    " ON users.user_id = expenses.user_id " +
                    "WHERE users.user_id = ?";
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(sql);
            databaseConnector.preparedStatement.setInt(1, user.getUser_id());
            databaseConnector.executePreparedSelectStatement();
            int i = 0;
            while (databaseConnector.resultSet.next()) {
                Expense expense = new Expense();
                expense.setExpense_id(databaseConnector.resultSet.getInt("expense_id"));
                expense.setExpense_name(databaseConnector.resultSet.getString("expense_name"));
                expense.setExpense_category(databaseConnector.resultSet.getString("expense_category"));
                expense.setExpense_amount(databaseConnector.resultSet.getDouble("expense_amount"));
                expense.setUser_id(user.getUser_id());
                expenses[i] = expense;
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
        return expenses;
    }

    public static Income[] getIncomes(User user){
        Income[] incomes = null;
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {

            // Get the number of incomes from the query, to dynamically increase or decrease the size of the array
            databaseConnector.openConnection();
            String SQL = "SELECT COUNT(income.income_id) FROM income INNER JOIN users ON users.user_id = income.user_id" +
                    " WHERE users.user_id = ?";
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(SQL);
            databaseConnector.preparedStatement.setInt(1, user.getUser_id());
            databaseConnector.executePreparedSelectStatement();
            databaseConnector.resultSet.next();
            int numIncomes = databaseConnector.resultSet.getInt("COUNT(income.income_id)");
            databaseConnector.closeConnection();

            // Query the accounts and map them to account objects, then populate array

            incomes = new Income[numIncomes]; // Accounts Array to be returned

            databaseConnector.openConnection();
            SQL = "SELECT income.* FROM income INNER JOIN users ON users.user_id = income.user_id WHERE users.user_id = ?";
            databaseConnector.preparedStatement = databaseConnector.prepareStatement(SQL);
            databaseConnector.preparedStatement.setInt(1, user.getUser_id());
            databaseConnector.executePreparedSelectStatement();
            int i = 0;
            while (databaseConnector.resultSet.next()) {
                Income inc = new Income();
                inc.setIncome_id(databaseConnector.resultSet.getInt("income_id"));
                inc.setIncome_source(databaseConnector.resultSet.getString("income_source"));
                inc.setIncome_amount(databaseConnector.resultSet.getDouble("income_amount"));
                inc.setIncome_per_month(databaseConnector.resultSet.getInt("income_per_month"));
                int boolInt = databaseConnector.resultSet.getInt("is_one_time");
                if (boolInt == 1) {
                    inc.setIs_one_time(true);
                } else {
                    inc.setIs_one_time(false);
                }
                inc.setUser_id(databaseConnector.resultSet.getInt("user_id"));
                incomes[i] = inc;
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
        return incomes;
    }

}
