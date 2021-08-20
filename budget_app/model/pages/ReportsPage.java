package budget_app.model.pages;

import budget_app.data.User;
import budget_app.model.Page;

public class ReportsPage extends Page {
    User user;

    public ReportsPage(User user) {
        this.user = user;
    }

    @Override
    public void printPage() {

    }

    @Override
    public Page pageOperations() {
        return null;
    }
}
