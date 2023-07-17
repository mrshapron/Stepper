package Input;


import Flow.Definition.StepperDefinition;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public interface InitializerData {
    StepperDefinition InitializeStepper(String filePath);

    StepperDefinition InitializeStepperViaFile(InputStream fileContent);
}
