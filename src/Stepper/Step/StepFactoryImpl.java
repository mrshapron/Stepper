package Stepper.Step;

import Stepper.Step.Implementation.CollectFilesInFolderStep;

public class StepFactoryImpl implements StepFactory {
    @Override
    public StepDefinition MakeStep(String stepName) {
        switch (stepName) {
            case "Collect Files In Folder":
                return StepDefinitionRegistry.COLLECT_FILES_IN_FOLDER.getStepDefinition();
            case "CSV Exporter":
                return StepDefinitionRegistry.CSV_EXPORTER.getStepDefinition();
            case "File Dumper":
                return StepDefinitionRegistry.FILE_DUMPER.getStepDefinition();
            case "Files Content Extractor":
                return StepDefinitionRegistry.FILES_CONTENT_EXTRACTOR.getStepDefinition();
            case "Files Renamer":
                return StepDefinitionRegistry.FILES_RENAMER.getStepDefinition();
            case "Properties Exporter":
                return StepDefinitionRegistry.PROPERTIES_EXPORTER.getStepDefinition();
            case "Spend Some Time":
                return StepDefinitionRegistry.SPEND_SOME_TIME.getStepDefinition();
        }
        return null;
    }
}
