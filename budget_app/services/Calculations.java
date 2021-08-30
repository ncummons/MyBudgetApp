package budget_app.services;

import budget_app.data.*;
import budget_app.model.*;

public class Calculations {

    public static double accountsTotal(Account[] accounts){
        double totalAccounts = 0;
        for (Account a: accounts) {
            totalAccounts += a.getAccount_balance();
        }
        return totalAccounts;
    }

    public static double debtsTotal(Debt[] debts){
        double totalDebts = 0;
        for (Debt d: debts) {
            totalDebts += d.getAmount();
        }
        return totalDebts;
    }

    public static double expensesTotal(Expense[] expenses){
        double totalExpenses = 0;
        for (Expense e: expenses) {
            totalExpenses += e.getExpense_amount();
        }
        return totalExpenses;
    }

    public static double incomeTotal(Income[] incomes){
        double totalIncome = 0;
        for (Income i: incomes) {
            totalIncome += i.getIncome_amount();
        }
        return totalIncome;
    }

    public static int monthsToPayOff(Debt debt, Account account){
        // Be sure to add in interest (not 100% sure how this will go mathematically)
        return 0;
    }

    public static double spendingToSavingRatio(Expense[] expenses, CustomGoal[] customGoals){
        return expensesTotal(expenses) / totalSavings(customGoals);
    }

    public static double debtToIncomeRatio(Income[] incomes, Debt[] debts){
        return debtsTotal(debts) / incomeTotal(incomes);
    }

    public static double totalSavings(CustomGoal[] customGoals){
        double totalSavingsPerMonth = 0;
        for (CustomGoal c: customGoals) {
            totalSavingsPerMonth += c.getMonthly_contribution();
        }
        return totalSavingsPerMonth;
    }

    public static int customGoalMonthsToGoal(CustomGoal customGoal) {
        int monthsToGoal = (int)Math.ceil((customGoal.getTotal_needed() - customGoal.getAmount_saved())
                / customGoal.getMonthly_contribution());
        return monthsToGoal;
    }

    public static double amountToGoal(CustomGoal customGoal) {
        double amountLeft = customGoal.getTotal_needed() - customGoal.getAmount_saved();
        return amountLeft;
    }

}
