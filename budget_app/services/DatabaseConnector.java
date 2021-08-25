package budget_app.services;
import java.sql.*;

public class DatabaseConnector {

    public Connection connection = null;
    public Statement statement = null;
    public PreparedStatement preparedStatement = null;
    public ResultSet resultSet = null;


    // Neither of these methods are all that usable in their current state. Try reworking later.

    public String writeSelectQuery(String table, String whereClause , String... column){
        String columns = "";
        if(column.length > 1){
            for (String c: column) {
            columns = columns + ", " + c;
            }
        } else {
            columns = column[0];
        }
        String query = "select " + columns + " from " + table + " " + whereClause + ";";
        return query;
    }

    public String writeSelectQuery(String table, String joinTable,
                                   String keyColumn, String whereColumn, String whereValue, String... column){
        String columns = "";
        if(column.length > 1){
            for (String c: column) {
                columns = columns + ", " + c;
            }
        } else {
            columns = column[0];
        }
        String query = "select " + columns + " from " + table + " INNER JOIN " + joinTable + " ON " + joinTable +
                "." + keyColumn + " = " + table + "." + keyColumn + " WHERE " + whereColumn + " = " + whereValue + ";";

        return query;
    }

    public ResultSet executeQuery(String query){
        try {
            statement = connection.createStatement();
            // Execute the query on the Statement, getting a ResultSet in return
            resultSet = statement.executeQuery(query);
        } catch (SQLException exc) {
            System.out.println("Exception occurred");
            exc.printStackTrace();
        }
        return resultSet;
    }

    /** Similar to general update statement, error message is different
     INSERT INTO table(column, column, column) VALUES(?, ?, ?) */

    public PreparedStatement prepareStatement(String sqlInsert){
        try{
            preparedStatement = connection.prepareStatement(sqlInsert);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return preparedStatement;
    }

    public String writePreparedInsertStatement(String table, String...column){
        String columns = "";
        int numColumns = column.length;
        String valuePlaceHolders = "(?";
        if(column.length > 1){
            for (String c: column) {
                columns = columns + ", " + c;
            }
        } else {
            columns = column[0];
        }
        if(numColumns > 1) {
            for (int i = 1; i < numColumns; i++) {
                valuePlaceHolders = valuePlaceHolders + ", ?";
            }
        }
        valuePlaceHolders = valuePlaceHolders + ")";
        String query = "INSERT INTO " + table + "(" + columns + " VALUES" + valuePlaceHolders + ";";

        return query;
    }

    public void executePreparedInsertStatement( ){
        try {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Returns the number of rows affected

    public int executePreparedUpdateStatement(){
        int ret = 0;
        try {
            ret = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public void executePreparedSelectStatement(){
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Used for writing general updates for the time being as well

    public int executeUpdateStatement(String update){
        int returnInt = 0;
        try{
            statement = connection.createStatement();
            returnInt = statement.executeUpdate(update);
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Update failed");
        }

        return returnInt;
    }

    public boolean executeDeleteStatement(String delete){
        boolean successful = false;
        try{
            statement = connection.createStatement();
            successful = statement.execute(delete);
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Delete failed.");
        }
        return successful;
    }

    public void closeConnection(){
        try {
            // close all JDBC elements
            if(statement != null) {
                statement.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if(resultSet != null) {
                resultSet.close();
            }
            if(connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void openConnection() {

        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");

             /* Other examples include:
                 Class.forname("oracle.jdbc.driver.oracledriver");
                 Class.forName("org.postgresql.Driver");
                 Class.forName("mongodb.jdbc.mongodriver"); */


            // configure the "connection string" (below) with the following format
            // -------------------------------------
            // jdbc:/mysql://H.O.S.T:<PORT>/DATABASE
            // -------------------------------------
            // on our localhost we don't need the port ":3306", we will also
            // add the login credentials and one or more other arguments
            // within the connection String. Your username here is most
            // likely "root", unless you have already created
            // new database users. The default MySQL username is "root".


            // NOTE: depending on your version of MySQL, you may need to delete "&useSSL=false"
            // from the connection String below in order to connect to the database


            String connectionString = "jdbc:mysql://localhost/budgetappdb?"
                    + "user=root&password=p@ssw3rd"
                    + "&useSSL=false&allowPublicKeyRetrieval=true";


            // Example connection String to remote MySQL instance:
            // "jdbc:mysql://178.34.21.99:3306/codingnomads?user=root&password=3tr0ngP@33W0rd"


            // Setup the connection with the DB
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException exc) {
            System.out.println("Exception occurred");
            exc.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Exception occured - driver not found on classpath");
            e.printStackTrace();
        }
    }

    // Used to test the connection with local database

    public static void testConnection(){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
        // This will load the MySQL driver, each DB has its own driver
        Class.forName("com.mysql.cj.jdbc.Driver");

             /* Other examples include:
                 Class.forname("oracle.jdbc.driver.oracledriver");
                 Class.forName("org.postgresql.Driver");
                 Class.forName("mongodb.jdbc.mongodriver"); */


        // configure the "connection string" (below) with the following format
        // -------------------------------------
        // jdbc:/mysql://H.O.S.T:<PORT>/DATABASE
        // -------------------------------------
        // on our localhost we don't need the port ":3306", we will also
        // add the login credentials and one or more other arguments
        // within the connection String. Your username here is most
        // likely "root", unless you have already created
        // new database users. The default MySQL username is "root".


        // NOTE: depending on your version of MySQL, you may need to delete "&useSSL=false"
        // from the connection String below in order to connect to the database


        String connectionString = "jdbc:mysql://localhost/budgetappdb?"
                + "user=root&password=p@ssw3rd"
                + "&useSSL=false&allowPublicKeyRetrieval=true";


        // Example connection String to remote MySQL instance:
        // "jdbc:mysql://178.34.21.99:3306/codingnomads?user=root&password=3tr0ngP@33W0rd"


        // Setup the connection with the DB
        connection = DriverManager.getConnection(connectionString);


        // Statements allow us to issue SQL queries to the database
        statement = connection.createStatement();
        // Execute the query on the Statement, getting a ResultSet in return
        resultSet = statement.executeQuery("select * from users");


        // loop through the result set while there are more records
        while (resultSet.next()) {
            // get the id, name and units fields from the result set and assign them to local variables
            int id = resultSet.getInt("user_id");
            String username = resultSet.getString("username");
            String firstName = resultSet.getString("first_name");


            // print out the result
            System.out.println("User id: " + id + " is " + username + " or " + firstName);
        }
    } catch (SQLException exc) {
        System.out.println("Exception occurred");
        exc.printStackTrace();
    } catch (ClassNotFoundException e) {
        System.out.println("Exception occured - driver not found on classpath");
        e.printStackTrace();
    } finally {
        try {
            // close all JDBC elements
            statement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

}
