package budget_app.controllers;

import budget_app.model.*;


public class Main {

    public static void main(String[] args) {
        Application application = new Application();
        application.setRunning(true);
        while(application.isRunning()){
            application.runApplication();
        }
        System.out.println("The application has terminated");
    }
}
