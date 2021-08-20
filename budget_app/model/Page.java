package budget_app.model;

import budget_app.controllers.UserInterface;

public abstract class Page {

    /** Each page will have a certain operation and pass to the Application another page
     * until the user wants to end the application, in which case the current page will pass
     * a null page to the Application, causing it to "stop running" and pass that boolean up to the Main
     * method, effectively terminating the application
     *
     * A page consists of two abstract methods for printing out the page messages for the user as well as
     * the page "operations" which return the next page when they are completed (which can be null)
     *
     *
     * The other methods are simply for taking user input. These will be used in the various pages to take inputs
     * either as strings, ints, or doubles.
     */

    public abstract void printPage();
    public abstract Page pageOperations();
    public int takeUserInputInt(){
        UserInterface userInterface = new UserInterface();
        return userInterface.takeInputInt();
    }
    public double takeUserInputDouble(){
        UserInterface userInterface = new UserInterface();
        return userInterface.takeInputDouble();
    }
    public String takeUserInputString(){
        UserInterface userInterface = new UserInterface();
        return userInterface.takeInputString();
    }
}
