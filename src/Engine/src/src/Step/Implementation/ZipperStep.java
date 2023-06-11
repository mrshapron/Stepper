package src.Step.Implementation;

import DataDefinitionPack.DataDefinitionRegistry;
import DataDefinitionPack.Implemantion.CustomType.EnumZip;
import Flow.Execution.Context.StepExecutionContext;
import Step.AbstractStepDefinition;
import Step.DataNecessity;
import Step.Declaration.DataDefinitionDeclarationImpl;
import Step.StepResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipperStep extends AbstractStepDefinition {

    public ZipperStep(){
        super("Zipper", true);

        addInput(new DataDefinitionDeclarationImpl("SOURCE", DataNecessity.MANDATORY, "Source", DataDefinitionRegistry.STRING));
        addInput(new DataDefinitionDeclarationImpl("OPERATION", DataNecessity.MANDATORY, "Operation type", DataDefinitionRegistry.ENUMERATOR));

        addOutput(new DataDefinitionDeclarationImpl("RESULT", DataNecessity.NA, "Zip operation result", DataDefinitionRegistry.STRING));
    }

    @Override
    public StepResult invoke(StepExecutionContext context) {
        String sourcePath = context.getDataValue("SOURCE", String.class);
        EnumZip operationZip = context.getDataValue("OPERATION", EnumZip.class);
        context.addLog(String.format("About to perform operation %s on source %s", sourcePath, operationZip));
        if(sourcePath == null || sourcePath == ""){
            context.addLog("source path is empty, step returns FAIL");
            context.storeDataValue("RESULT", "source path is empty, step returns FAIL");
            return StepResult.FAILURE;
        }
        if(operationZip == EnumZip.UNZIP){
            if(!sourcePath.endsWith(".zip")){
                context.addLog("source path doesn't end with .zip extension, returns fail");
                context.storeDataValue("RESULT", "source path doesn't end with .zip extension, returns fail");
                return StepResult.FAILURE;
            }
            try {
                unzip(sourcePath, sourcePath);
                context.addLog("unZipping files successfully done!");
                context.storeDataValue("RESULT","UnZipping files successfully done!");
                return StepResult.SUCCESS;
            } catch (IOException e) {
                context.addLog("Error occurred while unzipping the files: " + e.getMessage());
                context.storeDataValue("RESULT", "Error occurred while unzipping the files: " + e.getMessage());
                return StepResult.FAILURE;
            }
        }
        else{
            try {
                zip(sourcePath, sourcePath);
                System.out.println("Files zipped successfully!");
                context.storeDataValue("RESULT", "Zipping files successfully done!");
                context.addLog("Zipping files successfully done!");
                return StepResult.SUCCESS;

            } catch (IOException e) {
                context.storeDataValue("RESULT","Error occurred while zipping the files: " + e.getMessage());
                context.addLog("Error occurred while zipping the files: " + e.getMessage());
                return StepResult.FAILURE;
            }
        }
    }

    private static void zip(String sourceDir, String zipFilePath) throws IOException {
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            File sourceDirectory = new File(sourceDir);
            zipFile(sourceDirectory, sourceDirectory.getName(), zipOut);
        }
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }

        if (fileToZip.isDirectory()) {
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + File.separator + childFile.getName(), zipOut);
            }
        } else {
            try (FileInputStream fis = new FileInputStream(fileToZip)) {
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipOut.putNextEntry(zipEntry);

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    zipOut.write(buffer, 0, bytesRead);
                }

                zipOut.closeEntry();
            }
        }
    }

    private static void unzip(String zipFilePath, String destDir) throws IOException {
        File dir = new File(destDir);
        // create destination directory if it doesn't exist
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                String filePath = destDir + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // create intermediate directories if they don't exist
                    new File(filePath).getParentFile().mkdirs();
                    extractFile(zipIn, filePath);
                } else {
                    // create directory if it doesn't exist
                    new File(filePath).mkdirs();
                }
                zipIn.closeEntry();
            }
        }
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = zipIn.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }



}
