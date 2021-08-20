package budget_app.data;

public class CustomGoal {

    private int custom_goal_id;
    private String goal_name;
    private double monthly_contribution;
    private double total_needed;
    private double amount_saved;
    private int user_id;

    public CustomGoal(String goal_name, double monthly_contribution, double total_needed, double amount_saved, int user_id) {
        this.goal_name = goal_name;
        this.monthly_contribution = monthly_contribution;
        this.total_needed = total_needed;
        this.amount_saved = amount_saved;
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public CustomGoal() {}

    public int getCustom_goal_id() {
        return custom_goal_id;
    }

    public void setCustom_goal_id(int custom_goal_id) {
        this.custom_goal_id = custom_goal_id;
    }

    public String getGoal_name() {
        return goal_name;
    }

    public void setGoal_name(String goal_name) {
        this.goal_name = goal_name;
    }

    public double getMonthly_contribution() {
        return monthly_contribution;
    }

    public void setMonthly_contribution(double monthly_contribution) {
        this.monthly_contribution = monthly_contribution;
    }

    public double getTotal_needed() {
        return total_needed;
    }

    public void setTotal_needed(double total_needed) {
        this.total_needed = total_needed;
    }

    public double getAmount_saved() {
        return amount_saved;
    }

    public void setAmount_saved(double amount_saved) {
        this.amount_saved = amount_saved;
    }
}
