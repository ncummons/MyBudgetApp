package budget_app.model.pages;


import budget_app.data.*;
import budget_app.model.Page;
import budget_app.services.Calculations;
import budget_app.services.SQLQueries;

import java.sql.SQLException;


public class ReportsPage extends Page {
    User user;

    public ReportsPage(User user) {
        this.user = user;
    }

    @Override
    public void printPage() {
        System.out.println("______________________________________________________________________________");
        System.out.println("Welcome to the Financial Reports Page, where you can get an overview of your financials!");
        System.out.println();
        System.out.println("When you would like to move back to the main menu, simply type \"1\".");
        System.out.println("If you would like to view an Estimate of your total Account Balances in the future, simply type \"2\".");
        System.out.println("If you would like to view Debt reports, simply type \"3\".");
        System.out.println("If you would like to view a breakdown of your Spending vs. Saving, simply type \"4\".");
        System.out.println("If you would like to view an overview of your goals, simply type \"5\".");
        System.out.println("If you would like to view the Financial Sanity Report, simply type \"6\".");
        System.out.println("______________________________________________________________________________");
    }

    @Override
    public Page pageOperations() {
        printPage();
        int input = takeUserInputInt();
        switch (input) {
            case 1:
                return new MainMenu(user);

            case 2:
                accountBalanceEstimate();
                return pageOperations();

            case 3:
                debtReporting();
                return pageOperations();

            case 4:
                spendingSavingReport();
                return pageOperations();

            case 5:
                goalsOverview();
                return pageOperations();

            case 6:
                financialSanityReport();
                return pageOperations();
                
            default:
                System.out.println("Invalid input, try again.");
                return pageOperations();
        }
    }

    private void financialSanityReport() {
        Expense[] expenses = SQLQueries.getExpenses(this.user);
        Income[] incomes = SQLQueries.getIncomes(this.user);
        Debt[] debts = SQLQueries.getDebts(this.user);
        CustomGoal[] customGoals = SQLQueries.getCustomGoals(this.user);
        Account[] accounts = SQLQueries.getAccounts(this.user);
        double netIncome = Calculations.netIncome(expenses,incomes);
        double spendingToSavings = Calculations.spendingToSavingRatio(expenses,customGoals);
        double debtToIncome = Calculations.debtToIncomeRatio(incomes,debts);
        double totalDebt = Calculations.debtsTotal(debts);
        double totalIncome = Calculations.incomeTotal(incomes);
        double totalExpenses = Calculations.expensesTotal(expenses);
        double totalAccounts = Calculations.accountsTotal(accounts);
        System.out.println("______________________________________________________________________________");
        System.out.println("Welcome to your financial sanity report. You'll find your stats below.");
        System.out.print("Total Account Balances: $");
        System.out.printf("%.2f", totalAccounts);
        System.out.print(" | ");
        System.out.print("Total Income: $");
        System.out.printf("%.2f", totalIncome);
        System.out.print(" | ");
        System.out.print("Total Expenses: $");
        System.out.printf("%.2f", totalExpenses);
        System.out.print(" | ");
        System.out.print("Total Debt: $");
        System.out.printf("%.2f", totalDebt);
        System.out.print(" | ");
        System.out.println();
        System.out.print("Net Monthly Income: $");
        System.out.printf("%.2f", netIncome);
        System.out.print(" | ");
        System.out.print("Spending to Saving Ratio: ");
        System.out.printf("%.2f", spendingToSavings);
        System.out.print(" | ");
        System.out.print("Debt to Income Ratio: ");
        System.out.printf("%.2f", debtToIncome);
        System.out.print(" | ");
        System.out.println();
        System.out.println("______________________________________________________________________________");

    }

    private void goalsOverview() {
        CustomGoal[] goals = SQLQueries.getCustomGoals(this.user);
        for (CustomGoal c: goals) {
            int monthsToGoal = Calculations.customGoalMonthsToGoal(c);
            double amountLeft = Calculations.amountToGoal(c);
            System.out.println("______________________________________________________________________________");
            System.out.print("Goal ID: " + c.getCustom_goal_id() + " | Goal Name: " + c.getGoal_name() + " |");
            System.out.println();
            System.out.println("Total Amount Needed: $" + c.getTotal_needed() +
                    " | Monthly Contribution: $" + c.getMonthly_contribution() +
                    " | Amount saved: $" + c.getAmount_saved() + "\n" +
                    "Amount left to save: $" + amountLeft +
                    " | Months until savings goal accomplished: " + monthsToGoal + " months |");
            System.out.println("______________________________________________________________________________");
        }
        System.out.println("Total savings for goals: $" + Calculations.totalSavings(goals));
    }

    private void spendingSavingReport() {

        double spendingToSaving = Calculations.spendingToSavingRatio(SQLQueries.getExpenses(this.user), SQLQueries.getCustomGoals(this.user));
        System.out.println("____________________________________________________________________________________");
        System.out.print("Your spending to saving ratio is: ");
        System.out.printf("%.2f", spendingToSaving);
        System.out.println();
        System.out.print("Total Spending: $");
        System.out.printf("%.2f", Calculations.expensesTotal(SQLQueries.getExpenses(this.user)));
        System.out.print(" | Total Savings: $");
        System.out.printf("%.2f", Calculations.totalSavings(SQLQueries.getCustomGoals(this.user)));
        System.out.println();
        System.out.println("____________________________________________________________________________________");

    }

    private void debtReporting() {
        Debt[] debts = SQLQueries.getDebts(this.user);
        double debtToIncome = Calculations.
                debtToIncomeRatio(SQLQueries.getIncomes(this.user),debts);
        Debt highestUrgencyDebt = Calculations.highestInterestDebt(debts);
        boolean done = false;
        while(!done){
            System.out.println("____________________________________________________________________________________");
            System.out.println("What debt information would you like to see?");
            System.out.println("1) Debt to Income Ratio.");
            System.out.println("2) Highest Urgency Debt.");
            System.out.println("3) Debt Payoff Simulations.");
            System.out.println("4) Months To Pay off Each Debt.");
            System.out.println("5) Total Debt.");
            System.out.println("6) Return to reports menu.");
            System.out.println("____________________________________________________________________________________");
            int input = takeUserInputInt();
            switch (input) {
                case 1:
                    System.out.println("____________________________________________________________________________________");
                    System.out.print("Debt to Income Ratio: ");
                    System.out.printf("%.2f", debtToIncome);
                    System.out.println();
                    System.out.println("____________________________________________________________________________________");
                    break;

                case 2:
                    System.out.println("____________________________________________________________________________________");
                    System.out.print(" | Highest Urgency Debt: " + highestUrgencyDebt.getDebt_name() + " | interest rate: "
                            + highestUrgencyDebt.getInterest_rate() + "% | Months to pay off: "
                            + Calculations.monthsToPayOff(highestUrgencyDebt) + " |");
                    System.out.println();
                    System.out.println("____________________________________________________________________________________");
                    break;

                case 3:
                    System.out.println("____________________________________________________________________________________");
                    debtPayOffSimulations(debts);
                    System.out.println("____________________________________________________________________________________");
                    break;

                case 4:
                    for (Debt d : debts) {
                        int months = Calculations.monthsToPayOff(d);
                        System.out.println("Debt Name: " + d.getDebt_name() +
                                " | Months to pay off: " + months + " Months |");

                    }
                    break;

                case 5:
                    System.out.println("____________________________________________________________________________________");
                    System.out.print("Total Debt: $");
                    System.out.printf("%.2f", Calculations.debtsTotal(debts));
                    System.out.println();
                    System.out.println("____________________________________________________________________________________");
                    break;

                case 6:
                    done = true;
                    break;

                default:
                    System.out.println("Please enter a valid number.");
            }

        }

    }

    private Debt chooseDebtToAnalyze(Debt[] debts){
        System.out.println("Which Debt would you like to analyze? To Cancel, just type \"0\".");
        int counter = 5;
        Debt debtToAnalyze = null;
        for(Debt d: debts){
            System.out.println("Debt Name : " + d.getDebt_name() + "| Debt ID: " + d.getDebt_id());
            counter++;
            if(counter % 5 == 0){
                System.out.println();
            }
        }
        try{
            System.out.print("Debt ID: ");
            int debtID = takeUserInputInt();
            if(debtID == 0){
                return null;
            }
            debtToAnalyze = SQLQueries.getDebt(debtID);
        } catch (Exception e){
            System.out.println("Invalid entry. Please try again.");
            chooseDebtToAnalyze(debts);
        }
        return debtToAnalyze;
    }

    private void debtPayOffSimulations(Debt[] debts) {
        Debt debtToAnalyze = chooseDebtToAnalyze(debts);
        if (debtToAnalyze == null){ return; }
        boolean isDone = false;
        while(!isDone) {
            System.out.println("Current Months to Pay off: " + Calculations.monthsToPayOff(debtToAnalyze));
            System.out.println("By what percentage would you like to change your payment?");
            System.out.print("Percentage change: ");
            double percentChange = takeUserInputDouble();
            int newMonthsToPayOff = Calculations.percentageChangePayOff(debtToAnalyze, percentChange);
            System.out.println("If you change your payment by " + percentChange + "%, it would take you "
                    + newMonthsToPayOff + " months to pay off the debt.");
            System.out.println("Would you like to see a different percentage? Type \"0\" to exit or any other number to continue.");
            int response = takeUserInputInt();
            if (response == 0) {
                isDone = true;
            }
        }
    }

    private void accountBalanceEstimate() {
        Account[] accounts = SQLQueries.getAccounts(this.user);
        Expense[] expenses = SQLQueries.getExpenses(this.user);
        Income[] incomes = SQLQueries.getIncomes(this.user);

        double accountTotal = Calculations.accountsTotal(accounts);
        double netIncome = Calculations.netIncome(expenses,incomes);

        System.out.println("How many months into the future would you like to see " +
                "\nyour projected account balance based on current income and expenses?");
        System.out.print("Number of months: ");
        int numMonths = takeUserInputInt();
        if(numMonths <= 0){
            System.out.println("Please select a number greater than 0.");
            accountBalanceEstimate();
        }
        double futureBalance = accountTotal + (netIncome * numMonths);

        System.out.println("____________________________________________________________________________________");
        System.out.print("Current Total Account Balance: $");
        System.out.printf("%.2f", accountTotal);
        System.out.println();
        System.out.print("Future Projected Total Account Balance after " + numMonths + " months: $");
        System.out.printf("%.2f", futureBalance);
        System.out.println();
        System.out.println("____________________________________________________________________________________");
    }

}