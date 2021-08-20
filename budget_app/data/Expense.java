package budget_app.data;

public class Expense {
    private int expense_id;
    private String expense_name;
    private String expense_category;
    private double expense_amount;
    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Expense(String expense_name, String expense_category, double expense_amount, int user_id) {
        this.expense_name = expense_name;
        this.expense_category = expense_category;
        this.expense_amount = expense_amount;
        this.user_id = user_id;
    }

    public Expense() {
    }

    public Expense(int expense_id, String expense_name, String expense_category, double expense_amount) {
        this.expense_id = expense_id;
        this.expense_name = expense_name;
        this.expense_category = expense_category;
        this.expense_amount = expense_amount;
    }

    public int getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(int expense_id) {
        this.expense_id = expense_id;
    }

    public String getExpense_name() {
        return expense_name;
    }

    public void setExpense_name(String expense_name) {
        this.expense_name = expense_name;
    }

    public String getExpense_category() {
        return expense_category;
    }

    public void setExpense_category(String expense_category) {
        this.expense_category = expense_category;
    }

    public void setExpense_category(Categories expense_category) {
        switch (expense_category){
            case HOUSING: this.expense_category = "Housing"; break;
            case RESTAURANTS: this.expense_category = "Restaurants";  break;
            case MEDICAL: this.expense_category = "Medical";  break;
            case INSURANCE: this.expense_category = "Insurance";  break;
            case UTILITIES: this.expense_category = "Utilities";  break;
            case GROCERIES: this.expense_category = "Groceries";  break;
            case ALCOHOL: this.expense_category = "Alcohol";  break;
            case ENTERTAINMENT: this.expense_category = "Entertainment";  break;
            case CLOTHING: this.expense_category = "Clothing";  break;
            case MISCELLANEOUS: this.expense_category = "Miscellaneous";  break;
            default: this.expense_category = "Unknown";
        }
    }

    public double getExpense_amount() {
        return expense_amount;
    }

    public void setExpense_amount(double expense_amount) {
        this.expense_amount = expense_amount;
    }

    public static String getStringFromCategory(Categories expense_category) {
        switch (expense_category){
            case HOUSING: return "Housing";
            case RESTAURANTS: return "Restaurants";
            case MEDICAL: return "Medical";
            case INSURANCE: return "Insurance";
            case UTILITIES: return"Utilities";
            case GROCERIES: return "Groceries";
            case ALCOHOL: return "Alcohol";
            case ENTERTAINMENT: return "Entertainment";
            case CLOTHING: return "Clothing";
            case MISCELLANEOUS: return "Miscellaneous";
            default: return "Unknown";
        }
    }

    public enum Categories {
        HOUSING,
        RESTAURANTS,
        MEDICAL,
        INSURANCE,
        UTILITIES,
        GROCERIES,
        ALCOHOL,
        ENTERTAINMENT,
        CLOTHING,
        MISCELLANEOUS
    }
}
