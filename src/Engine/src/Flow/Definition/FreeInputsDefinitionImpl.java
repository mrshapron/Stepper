package Flow.Definition;

import Step.Declaration.DataDefinitionDeclaration;

import java.util.ArrayList;
import java.util.List;

public class FreeInputsDefinitionImpl implements FreeInputsDefinition{
    private DataDefinitionDeclaration dataDefinitionDeclaration;
    private List<StepUsageDeclaration> stepUsageDeclarations;
    public  FreeInputsDefinitionImpl(
            DataDefinitionDeclaration dataDefinitionDeclaration){
        this.dataDefinitionDeclaration = dataDefinitionDeclaration;
        this.stepUsageDeclarations = new ArrayList<>();
    }
    @Override
    public DataDefinitionDeclaration getDataDefinitionDeclaration() {
        return dataDefinitionDeclaration;
    }

    @Override
    public List<StepUsageDeclaration> getStepUsageDeclarations() {
        return stepUsageDeclarations;
    }

    @Override
    public void addStepUsageDeclaration(StepUsageDeclaration stepUsageDeclaration) {
        stepUsageDeclarations.add(stepUsageDeclaration);
    }
}
