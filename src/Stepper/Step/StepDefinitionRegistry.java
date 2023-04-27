package Stepper.Step;

import Stepper.Step.Implementation.*;

public enum StepDefinitionRegistry {
    SPEND_SOME_TIME(new SpendSomeTimeStep()),
    COLLECT_FILES_IN_FOLDER(new CollectFilesInFolderStep()),
    FILES_RENAMER(new FilesRenamerStep()),

    CSV_EXPORTER(new CSVExporterStep()),
    FILE_DUMPER(new FileDumperStep()),
    PROPERTIES_EXPORTER(new PropertiesExporterStep()),
    FILES_CONTENT_EXTRACTOR(new FilesContentExtractorStep()),
    FILES_DELETER(new FilesDeleterStep());


    private final StepDefinition stepDefinition;
    StepDefinitionRegistry(StepDefinition stepDefinition) {
        this.stepDefinition = stepDefinition;
    }

    public StepDefinition getStepDefinition() {
        return stepDefinition;
    }
}
