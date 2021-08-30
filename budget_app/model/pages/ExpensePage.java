package budget_app.model.pages;

import budget_app.controllers.UserInterface;
import budget_app.data.Expense;
import budget_app.data.User;
import budget_app.model.Page;
import budget_app.services.*;


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
        SQLUpdates.updateExpense(this.user);
    }

    private void addExpenses() {
        SQLInserts.addExpenses(this.user);
    }

    // Need to find a way to format the output of this to print only two decimals (also for the other print totals)n

    private void printTotalExpenses(Expense[] expenses){
        double totalExpenses = Calculations.expensesTotal(expenses);
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
        Expense[] expenses = SQLQueries.getExpenses(this.user);
        return expenses;
    }

    public static Expense.Categories getCategoryFromUser(){
        int input = 0;
        Expense.Categories category = null;
        System.out.println("In what category is this expense?");
        System.out.println("1) Housing | 2) Restaurants | 3) Medical | 4) Insurance | 5) Utilities |");
        System.out.println("6) Groceries | 7) Alcohol | 8) Entertainment | 9) Clothing | 10) Miscellaneous |");
        try{
            boolean unconfirmed = true;
            while(unconfirmed) {
                System.out.print("Please enter the corresponding number here: ");
                input = UserInterface.takeInputInt();
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
