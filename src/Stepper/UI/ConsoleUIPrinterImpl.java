package Stepper.UI;

import Stepper.Flow.Defenition.FlowDefinition;
import Stepper.Flow.Defenition.FreeInputsDefinition;
import Stepper.Flow.Defenition.StepUsageDeclaration;
import Stepper.Flow.Execution.History.FlowHistoryData;
import Stepper.Flow.Execution.History.FreeInputHistoryData;
import Stepper.Flow.Execution.History.OutputHistoryData;
import Stepper.Flow.Execution.History.StepHistoryData;
import Stepper.Flow.Logger.FlowLog;
import Stepper.Step.Declaration.DataDefinitionDeclaration;

import java.time.format.DateTimeFormatter;

public class ConsoleUIPrinterImpl implements ConsoleUIPrinter {
    @Override
    public void printMenu() {
        System.out.println("****************MENU*****************");
        System.out.println("Choose Command: ");
        System.out.println("1. Read XML File to the System");
        System.out.println("2. Show All Flows in the System");
        System.out.println("3. Execute flow");
        System.out.println("4. Show Details of Past Flow");
        System.out.println("5. Statistics");
        System.out.println("6. Exit System");
        System.out.println("**************************************");
    }

    @Override
    public void printFlow(FlowDefinition flowDefinition) {
        System.out.format("Flow Name : %s\n", flowDefinition.getName());
        System.out.format("Flow Description : %s\n", flowDefinition.getDescription());
        System.out.print("Formal Outputs of the Flow : ");
        flowDefinition.getFlowFormalOutputs().forEach(formalOutput -> System.out.print(formalOutput + ", "));
        System.out.println();
        System.out.format("Is 'Read-Only' Flow : %s\n", flowDefinition.getIsReadOnly());
        System.out.println("Flow Steps :");
        int indexStep = 1;
        for (StepUsageDeclaration stepDefDec : flowDefinition.getFlowSteps()) {
            System.out.println(">Step Number " + (indexStep++ ) + ":");
            System.out.printf("Step Name : %s", stepDefDec.getStepDefinition().name());
            if (!stepDefDec.getFinalStepName().equals(stepDefDec.getStepDefinition().name()))
                System.out.printf(" Alias Step Name: %s", stepDefDec.getFinalStepName());
            System.out.println();
            System.out.printf("Is 'Read-Only' Step : %s\n", stepDefDec.getStepDefinition().isReadonly());
        }
        int indexFreeInput = 1;
        System.out.println("Free Inputs List :");
        for (FreeInputsDefinition freeInput : flowDefinition.getFlowFreeInputs()) {
            System.out.println("Input Number : " + (indexFreeInput++));
            System.out.printf("Input Final Name : %s\n",
                    freeInput.getDataDefinitionDeclaration().getAliasName());
            System.out.println("Type Input : " + freeInput.getDataDefinitionDeclaration().dataDefinition().getType());
            System.out.print("Connected to Steps : ");
            freeInput.getStepUsageDeclarations().forEach(stepConnected -> System.out.print(stepConnected.getFinalStepName() + ", "));
            System.out.println();
            System.out.println("Necessity Input : " + freeInput.getDataDefinitionDeclaration().necessity().toString().toLowerCase());
        }
        System.out.println("All Outputs in Flow:");
        int indexOutput = 1;
        for (StepUsageDeclaration stepUsageDeclaration : flowDefinition.getFlowSteps()) {
            for (DataDefinitionDeclaration output : stepUsageDeclaration.getStepDefinition().outputs()) {
                System.out.println("Output Number : " + indexOutput++);
                System.out.printf("Output Name : %s\n", output.getAliasName());
                System.out.printf("Output Type : %s\n", output.dataDefinition().getType());
                System.out.printf("Step Producer : %s\n", stepUsageDeclaration.getFinalStepName());
            }
        }
    }

    @Override
    public void printHistoryFlow(FlowHistoryData flowHistoryData) {
        System.out.format("Flow ID: %s\n", flowHistoryData.getFlowID());
        System.out.format("Flow Name: %s\n", flowHistoryData.getFlowName());
        System.out.format("Flow Result: %s\n", flowHistoryData.getFlowResult());
        System.out.format("Flow Runtime: %s\n", flowHistoryData.getRuntime());

        System.out.println("Flow Free Inputs: ");
        for (FreeInputHistoryData freeInputHistoryData : flowHistoryData.getFreeInputsHistory()) {
            System.out.format("Free Input Name (After Alias): %s\n", freeInputHistoryData.getFinalName());
            System.out.format("Free Input Type: %s\n", freeInputHistoryData.getType().getSimpleName());
            System.out.format("Free Input Necessity: %s\n", freeInputHistoryData.getNecessity());
            System.out.println("$$$$$$$$$$$");
        }

        System.out.println("Flow Outputs:");
        for (OutputHistoryData outputHistoryData : flowHistoryData.getOutputsHistoryData()) {
            System.out.format("Output Final name: %s\n", outputHistoryData.getFinalName());
            System.out.format("Output Type: %s\n", outputHistoryData.getType().getSimpleName());
            System.out.format("Output Data: %s\n", outputHistoryData.getData().toString());
            System.out.println("###########");
        }
        System.out.println("Steps Information:");
        for (StepHistoryData stepHistoryData : flowHistoryData.getStepsHistoryData()) {
            System.out.format("Step Name: %s\n",stepHistoryData.getName());
            System.out.printf("Step Runtime: %d ms\n", stepHistoryData.getRunTime());
            System.out.printf("Step Result: %s\n", stepHistoryData.getResult());
            System.out.printf("Summary Line step: %s\b", stepHistoryData.getSummary());
            System.out.println("Steps Logs:");
            for (FlowLog flowLog : stepHistoryData.getLogs()) {
                System.out.printf("#Time: [%s] Content: %s\n",flowLog.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")),
                        flowLog.getContent());
            }
            System.out.println("@@@@@@@@@@@@@");
        }
    }
}