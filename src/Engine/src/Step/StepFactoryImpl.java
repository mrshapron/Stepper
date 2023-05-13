package Step;


import Step.Implementation.*;

public class StepFactoryImpl implements StepFactory {
    @Override
    public StepDefinition MakeStep(String stepName) {
        switch (stepName) {
            case "Collect Files In Folder":
                return new CollectFilesInFolderStep();//StepDefinitionRegistry.COLLECT_FILES_IN_FOLDER.getStepDefinition();
            case "CSV Exporter":
                return new CSVExporterStep();//StepDefinitionRegistry.CSV_EXPORTER.getStepDefinition();
            case "File Dumper":
                return new FileDumperStep();//StepDefinitionRegistry.FILE_DUMPER.getStepDefinition();
            case "Files Content Extractor":
                return new FilesContentExtractorStep();//StepDefinitionRegistry.FILES_CONTENT_EXTRACTOR.getStepDefinition();
            case "Files Renamer":
                return new FilesRenamerStep();//StepDefinitionRegistry.FILES_RENAMER.getStepDefinition();
            case "Properties Exporter":
                return new PropertiesExporterStep();//StepDefinitionRegistry.PROPERTIES_EXPORTER.getStepDefinition();
            case "Spend Some Time":
                return new SpendSomeTimeStep();//StepDefinitionRegistry.SPEND_SOME_TIME.getStepDefinition();
            case "Files Deleter":
                return new FilesDeleterStep();
        }
        return null;
    }
}
