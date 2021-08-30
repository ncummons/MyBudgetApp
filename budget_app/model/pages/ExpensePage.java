package budget_app.model.pages;

import budget_app.data.Expense;
import budget_app.data.User;
import budget_app.model.Page;
import budget_app.services.DatabaseConnector;
import budget_app.services.SQLDeletes;

import java.sql.SQLException;

public class ExpensePage extends Page {

    User user;

    public ExpensePage(User user) {
        this.user = user;
    }

    @Override
    public void printPage() {
        System.out.println("______________________________________________________________________________");
        System.out.println("Welcome to the Monthly Expense page! You'll find your monthly expense information below.");
        System.out.println();
        System.out.println("When you would like to move back to the main menu, simply type \"1\".");
        System.out.println("If you would like to close out of the program,simply type \"2\".");
        System.out.println("If you would like to add monthly expenses, simply type \"3\".");
        System.out.println("If you would like to update expense information, simply type \"4\".");
        System.out.println("If you would like to delete an expense, simply type \"5\".");
        System.out.println("______________________________________________________________________________");
    }

    @Override
    public Page pageOperations() {
        printPage();
        Expense[] expenses = getExpenses();
        printExpenseInfo(expenses);
        printTotalExpenses(expenses);
        int input = takeUserInputInt();
        switch (input){
            case 1: return new MainMenu(user);

            case 2: return null;

            case 3: addExpenses(); return pageOperations();

            case 4: updateExpense(); return pageOperations();

            case 5: deleteExpense(); return pageOperations();

            default:
                System.out.println("Invalid input, try again.");
                return pageOperations();
    }
}

    private void deleteExpense() {
        SQLDeletes.deleteExpense(this.user);
    }

    private void updateExpense() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        int expenseID;
        System.out.println("To cancel, just type \"0\".");
        System.out.println("Which Expense would you like to update? Enter the corresponding expense id below: ");
        try {
            expenseID = takeUserInputInt();
            if (expenseID == 0){
                return;
            }
            databaseConnector.openConnection();
            databaseConnector.resultSet = databaseConnector.executeQuery("SELECT expenses.* FROM expenses" +
                    " INNER JOIN users" +
                    " ON users.user_id = expenses.user_id " +
                    "WHERE expenses.expense_id = " + expenseID + ";");

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
            String expenseName = takeUserInputString();
            System.out.println();
            System.out.print("Expense amount: $");
            double expenseAmount  = takeUserInputDouble();
            System.out.println();
            Expense.Categories cat = getCategoryFromUser();
            String categoryString = Expense.getStringFromCategory(cat);


            databaseConnector.executeUpdateStatement("UPDATE expenses " +
                    "SET expense_name = \"" + expenseName + "\", expense_amount = " + expenseAmount + ", " +
                    "expense_category = \"" + categoryString + "\" WHERE expenses.expense_id = " + expenseID + ";");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem updating your expense. Please try again later.");
        }finally {
            databaseConnector.closeConnection();
        }
    }

    private void addExpenses() {
        System.out.println("How many Monthly Expenses would you like to add?");
        int numExpenses = takeUserInputInt();
        DatabaseConnector databaseConnector = new DatabaseConnector();
        for(int i = 0; i < numExpenses; i++) {
            try {
                // take user input, prepare the sql insert statement, and execute the statement to insert the account
                System.out.println("To cancel, type \"cancel\" into Expense Name.");
                System.out.println("Expense Name: ");
                String expenseName = takeUserInputString();
                if(expenseName.compareToIgnoreCase("cancel") == 0){
                    i = numExpenses;
                    return;
                }
                System.out.println("Expense amount: ");
                System.out.print("$");
                double expenseAmount = takeUserInputDouble();
                System.out.println();

                Expense.Categories cat = getCategoryFromUser();
                String categoryString = Expense.getStringFromCategory(cat);


                String sqlInsert = "INSERT INTO expenses(expense_name, expense_category, " +
                        "expense_amount, user_id) VALUES(?, ?, ?, ?)";

                databaseConnector.openConnection();

                databaseConnector.preparedStatement = databaseConnector.prepareStatement(sqlInsert);
                databaseConnector.preparedStatement.setString(1, expenseName);
                databaseConnector.preparedStatement.setString(2, categoryString);
                databaseConnector.preparedStatement.setDouble(3, expenseAmount);
                databaseConnector.preparedStatement.setInt(4, user.getUser_id());

                databaseConnector.executePreparedInsertStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnector.closeConnection();
            }
        }
    }

    // Need to find a way to format the output of this to print only two decimals (also for the other print totals)n

    private void printTotalExpenses(Expense[] expenses){
        double totalExpenses = 0;
        for (Expense e: expenses) {
            totalExpenses += e.getExpense_amount();
        }
        System.out.print("Your total monthly expenses are: $");
        System.out.printf("%.2f", totalExpenses);
        System.out.println();
    }

    private void printExpenseInfo(Expense[] expenses) {
        for (Expense e: expenses) {
            System.out.print("Expense ID: " + e.getExpense_id() + " | Expense Name: " + e.getExpense_name() +
                    " | " + "Expense Category: " + e.getExpense_category());
            System.out.println();
            System.out.println("Expense Amount: $" + e.getExpense_amount());
            System.out.println("______________________________________________________________________________");
        }
    }

    private Expense[] getExpenses() {
        Expense[] expenses = null;
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {

            // Get the number of expenses from the query, to dynamically increase or decrease the size of the array
            databaseConnector.openConnection();
            databaseConnector.resultSet = databaseConnector.executeQuery("SELECT COUNT(expenses.expense_id) FROM expenses" +
                    " INNER JOIN users" +
                    " ON users.user_id = expenses.user_id " +
                    "WHERE users.user_id = " + user.getUser_id() + ";");
            databaseConnector.resultSet.next();
            int numExpenses = databaseConnector.resultSet.getInt("COUNT(expenses.expense_id)");
            databaseConnector.closeConnection();

            // Query the accounts and map them to account objects, then populate array

            expenses = new Expense[numExpenses]; // Accounts Array to be returned

            databaseConnector.openConnection();
            databaseConnector.resultSet = databaseConnector.executeQuery("SELECT expenses.* FROM expenses" +
                    " INNER JOIN users" +
                    " ON users.user_id = expenses.user_id " +
                    "WHERE users.user_id = " + user.getUser_id() + ";");
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

    private Expense.Categories getCategoryFromUser(){
        int input = 0;
        Expense.Categories category = null;
        System.out.println("In what category is this expense?");
        System.out.println("1) Housing | 2) Restaurants | 3) Medical | 4) Insurance | 5) Utilities |");
        System.out.println("6) Groceries | 7) Alcohol | 8) Entertainment | 9) Clothing | 10) Miscellaneous |");
        try{
            boolean unconfirmed = true;
            while(unconfirmed) {
                System.out.print("Please enter the corresponding number here: ");
                input = takeUserInputInt();
                System.out.println();
                if (input > 0 && input < 11){
                    unconfirmed = false;
                } else {
                    System.out.println("Please only input a number between 1 and 10.");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        switch (input){
            case 1: category = Expense.Categories.HOUSING; return category;
            case 2: category = Expense.Categories.RESTAURANTS; return category;
            case 3: category = Expense.Categories.MEDICAL; return category;
            case 4: category = Expense.Categories.INSURANCE; return category;
            case 5: category = Expense.Categories.UTILITIES; return category;
            case 6: category = Expense.Categories.GROCERIES; return category;
            case 7: category = Expense.Categories.ALCOHOL; return category;
            case 8: category = Expense.Categories.ENTERTAINMENT; return category;
            case 9: category = Expense.Categories.CLOTHING; return category;
            case 10: category = Expense.Categories.MISCELLANEOUS; return category;
            default: return category;
        }
    }
}
