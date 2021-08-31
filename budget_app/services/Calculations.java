package budget_app.services;

import budget_app.data.*;

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

    public static int percentageChangePayOff(Debt debt, double percentChange){
        double monthlyInterest = debt.getInterest_rate() / 1200;
        double monthlyPayment = debt.getMonthly_payment();
        double outstandingBalance = debt.getAmount();
        double numMonths;
        double newMonthlyPayment = monthlyPayment * (1 + (percentChange/100));
        numMonths = -(Math.log(1 - ((outstandingBalance * monthlyInterest)) / newMonthlyPayment) / Math.log(1 + monthlyInterest));
        int intMonths = (int)Math.ceil(numMonths);
        return intMonths;
    }

    public static int monthsToPayOff(Debt debt){
        //N = –[ln(1 – [(PV * i) / PMT_] ) / ln(1 + _i)]
        double monthlyInterest = debt.getInterest_rate() / 1200;
        double monthlyPayment = debt.getMonthly_payment();
        double outstandingBalance = debt.getAmount();
        double numMonths;
        numMonths = -(Math.log(1 - ((outstandingBalance * monthlyInterest) / monthlyPayment)) / Math.log(1 + monthlyInterest));
        int intMonths = (int)Math.ceil(numMonths);
        return intMonths;
    }

    public static double spendingToSavingRatio(Expense[] expenses, CustomGoal[] customGoals){
        return expensesTotal(expenses) / totalSavings(customGoals);
    }

    public static double netIncome(Expense[] expenses, Income[] incomes){
        return incomeTotal(incomes) - expensesTotal(expenses);
    }

    public static double debtToIncomeRatio(Income[] incomes, Debt[] debts){
        return debtsTotal(debts) / (incomeTotal(incomes) * 12);
    }

    public static Debt highestInterestDebt(Debt[] debts){
        Debt debt = null;
        double maxInterest = 0;
        for(Debt d: debts){
            if (d.getInterest_rate() > maxInterest){
                maxInterest = d.getInterest_rate();
                debt = d;
            }
        }
        return debt;
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
