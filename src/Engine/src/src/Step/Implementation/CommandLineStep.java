package src.Step.Implementation;

import DataDefinitionPack.DataDefinitionRegistry;
import DataDefinitionPack.Implemantion.StringDataDefinition;
import Flow.Execution.Context.StepExecutionContext;
import Step.AbstractStepDefinition;
import Step.DataNecessity;
import Step.Declaration.DataDefinitionDeclarationImpl;
import Step.StepResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandLineStep extends AbstractStepDefinition {
    public CommandLineStep() {
        super("Command Line", false);

        addInput(new DataDefinitionDeclarationImpl("COMMAND", DataNecessity.MANDATORY,"Command", DataDefinitionRegistry.STRING));
        addInput(new DataDefinitionDeclarationImpl("ARGUMENTS", DataNecessity.OPTIONAL,"Command Arguments", DataDefinitionRegistry.STRING));

        addOutput(new DataDefinitionDeclarationImpl("RESULT", DataNecessity.NA,"Command output", DataDefinitionRegistry.STRING));
    }

    @Override
    public StepResult invoke(StepExecutionContext context) {
        String command = context.getDataValue("COMMAND", String.class);
        String arguments = context.getDataValue("ARGUMENTS", String.class);
        context.addLog(String.format("About to invoke %s %s", command, arguments));
        try {
            // Command and arguments as a list
            List<String> commandList = new ArrayList<>();
            commandList.add(command);
            if(arguments != null && !arguments.isEmpty()){
                commandList.addAll(Arrays.stream(arguments.split(",")).collect(Collectors.toList()));
            }
            // Create the ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder(commandList);

            // Redirect the error stream to the standard output
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                result.append(line + "\n");
            }

            // Wait for the process to finish and get the exit code
            int exitCode = process.waitFor();
            result.append("Exit Code: " + exitCode);
            context.addLog(String.format("Command : run successfully with exit code: %d", command, exitCode));
            context.addSummaryLine(String.format("Command : run successfully with exit code: %d", command, exitCode));
            context.storeDataValue("RESULT", result);
            return StepResult.SUCCESS;
        } catch (IOException | InterruptedException e) {
            context.addLog(String.format("There was a problem running the command %s", command));
            context.addSummaryLine(String.format("There was a problem running the command %s", command));
            return StepResult.SUCCESS;
        }
    }
}
