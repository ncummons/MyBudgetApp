package budget_app.controllers;

import java.util.Scanner;

public class UserInterface {

    Scanner input = new Scanner(System.in);

    public String takeInputString(){
        boolean isDone = false;
        String ret = null;
        while(!isDone) {
            try {
                ret = input.next();
                isDone = true;
            } catch (Exception e) {
                System.out.println("Unable to take user input. Please try again.");
                input.nextLine();
            }
        }
        return ret;
    }

    public int takeInputInt(){
        boolean isDone = false;
        int ret = 0;
        while(!isDone) {
            try {
                ret = input.nextInt();
                isDone = true;
            } catch (Exception e) {
                System.out.println("Please enter a number, try again.");
                input.nextLine();
            }
        }
        return ret;
    }

    public double takeInputDouble(){
        boolean isDone = false;
        double ret = 0;
        while(!isDone) {
            try {
                ret = input.nextDouble();
                isDone = true;
            } catch (Exception e) {
                System.out.println("Please enter a number, try again.");
                input.nextLine();
            }
        }
        return ret;
    }


}
