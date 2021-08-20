package budget_app.data;

public class Account {
    private int account_id;
    private String account_name;
    private String bank_name;
    private double account_balance;
    private int user_id;

    public Account() {}

    public Account(String account_name, String bank_name, double account_balance, int user_id) {
        this.account_name = account_name;
        this.bank_name = bank_name;
        this.account_balance = account_balance;
        this.user_id = user_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public double getAccount_balance() {
        return account_balance;
    }

    public void setAccount_balance(double account_balance) {
        this.account_balance = account_balance;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
