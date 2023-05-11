package Stepper.UI;

public enum MainMenuChoice {
    READ_FILE(1), SHOW_FLOW(2),START_FLOW(3), PAST_DETAILS(4), STATISTICS(5), EXIT_SYSTEM(6);
    int number;
    MainMenuChoice(int choice){
        this.number = choice;
    }
}
