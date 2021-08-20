package budget_app.model;

import budget_app.data.*;
import budget_app.model.pages.UserLoginScreen;

public class Application {
    private boolean isRunning;
    private User currentUser;
    private Page currentPage = null;

    public void runApplication(){
        currentPage = new UserLoginScreen();
        while(isRunning) {
            Page nextPage = currentPage.pageOperations();
            setCurrentPage(nextPage);
            if(currentPage == null){
                isRunning = false;
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean isRunning){
        this.isRunning = isRunning;
    }

    public void setCurrentUser(User currentUser){
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Page currentPage) {
        this.currentPage = currentPage;
    }

    public Application() {
    }

    public Application(Page currentPage) {
        this.currentPage = currentPage;
    }
}
