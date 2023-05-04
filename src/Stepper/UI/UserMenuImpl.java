package Stepper.UI;

import java.sql.SQLOutput;
import java.util.Scanner;

public class UserMenuImpl implements UserMenu{

    @Override
    public void startMenu() {
        Scanner scanner = new Scanner(System.in);

        boolean exitProgram = false;
        while (!exitProgram){
            printMenu();
            int command = scanner.nextInt();
            switch (command){
                case 1: {

                }
            }
        }

    }

    private void printMenu(){
        System.out.println("Choose Command: ");
        System.out.println("1. Read XML File to the System");
        System.out.println("2. Show All Flows in the System");
        System.out.println("3. Execute flow");
    }

}