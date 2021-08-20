package budget_app.model.pages;

import budget_app.data.User;
import budget_app.model.Page;

public class CustomGoalsPage extends Page {
    User user;

    public CustomGoalsPage(User user) {
        this.user = user;
    }

    @Override
    public Page pageOperations() {
        return null;
    }

    @Override
    public void printPage(){
            }
}
