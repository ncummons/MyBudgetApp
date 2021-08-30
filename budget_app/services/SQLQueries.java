package budget_app.services;

import budget_app.data.Income;
import budget_app.data.User;

import java.sql.SQLException;

public class SQLQueries {

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
