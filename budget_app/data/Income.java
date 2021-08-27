package budget_app.data;

public class Income {
    private int income_id;
    private double income_amount;
    private String income_source;
    private int income_per_month;
    private boolean is_one_time;
    private int user_id;


    public Income(double income_amount, String income_source, int income_per_month, boolean is_one_time, int user_id) {
        this.income_amount = income_amount;
        this.income_source = income_source;
        this.income_per_month = income_per_month;
        this.is_one_time = is_one_time;
        this.user_id = user_id;
    }

    public Income() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getIncome_id() {
        return income_id;
    }

    public void setIncome_id(int income_id) {
        this.income_id = income_id;
    }

    public double getIncome_amount() {
        return income_amount;
    }

    public void setIncome_amount(double income_amount) {
        this.income_amount = income_amount;
    }

    public String getIncome_source() {
        return income_source;
    }

    public void setIncome_source(String income_source) {
        this.income_source = income_source;
    }

    public int getIncome_per_month() {
        return income_per_month;
    }

    public void setIncome_per_month(int income_per_month) {
        this.income_per_month = income_per_month;
    }

    public boolean isIs_one_time() {
        return is_one_time;
    }

    public void setIs_one_time(boolean is_one_time) {
        this.is_one_time = is_one_time;
    }

    public Income(int income_id, double income_amount, String income_source, int income_per_month, boolean is_one_time) {
        this.income_id = income_id;
        this.income_amount = income_amount;
        this.income_source = income_source;
        this.income_per_month = income_per_month;
        this.is_one_time = is_one_time;
    }

    public Income(int income_id, double income_amount, String income_source, boolean is_one_time) {
        this.income_id = income_id;
        this.income_amount = income_amount;
        this.income_source = income_source;
        this.is_one_time = is_one_time;
    }
}
