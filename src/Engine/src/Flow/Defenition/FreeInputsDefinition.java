package Flow.Defenition;


import Step.Declaration.DataDefinitionDeclaration;

import java.util.List;

public interface FreeInputsDefinition {
    DataDefinitionDeclaration getDataDefinitionDeclaration();
    List<StepUsageDeclaration> getStepUsageDeclarations();
    void addStepUsageDeclaration(StepUsageDeclaration stepUsageDeclaration);
}
