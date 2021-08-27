package budget_app.data;

public class Debt {
    private int debt_id;
    private String debt_name;
    private double amount;
    private double monthly_payment;
    private double interest_rate;
    private String lender_name;
    private int debt_due_date;
    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Debt() {
    }

    public Debt(String debt_name, double amount, double monthly_payment, double interest_rate, String lender_name, int debt_due_date, int user_id) {
        this.debt_name = debt_name;
        this.amount = amount;
        this.monthly_payment = monthly_payment;
        this.interest_rate = interest_rate;
        this.lender_name = lender_name;
        this.debt_due_date = debt_due_date;
        this.user_id = user_id;
    }

    public Debt(String debt_name, double amount, double interest_rate, String lender_name, int debt_due_date, int user_id) {
        this.debt_name = debt_name;
        this.amount = amount;
        this.interest_rate = interest_rate;
        this.lender_name = lender_name;
        this.debt_due_date = debt_due_date;
        this.user_id = user_id;
    }

    public Debt(double amount, double interest_rate, String lender_name, int debt_due_date, int user_id) {
        this.amount = amount;
        this.interest_rate = interest_rate;
        this.lender_name = lender_name;
        this.debt_due_date = debt_due_date;
        this.user_id = user_id;
    }

    public Debt(int debt_id, double amount, double interest_rate, String lender_name, int debt_due_date) {
        this.debt_id = debt_id;
        this.amount = amount;
        this.interest_rate = interest_rate;
        this.lender_name = lender_name;
        this.debt_due_date = debt_due_date;
    }

    public Debt(int debt_id, double amount, double interest_rate) {
        this.debt_id = debt_id;
        this.amount = amount;
        this.interest_rate = interest_rate;
    }

    public int getDebt_id() {
        return debt_id;
    }

    public void setDebt_id(int debt_id) {
        this.debt_id = debt_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(double interest_rate) {
        this.interest_rate = interest_rate;
    }

    public String getLender_name() {
        return lender_name;
    }

    public void setLender_name(String lender_name) {
        this.lender_name = lender_name;
    }

    public int getDebt_due_date() {
        return debt_due_date;
    }

    public void setDebt_due_date(int debt_due_date) {
        this.debt_due_date = debt_due_date;
    }

    public String getDebt_name() {
        return debt_name;
    }

    public void setDebt_name(String debt_name) {
        this.debt_name = debt_name;
    }

    public double getMonthly_payment() {
        return monthly_payment;
    }

    public void setMonthly_payment(double monthly_payment) {
        this.monthly_payment = monthly_payment;
    }
}

