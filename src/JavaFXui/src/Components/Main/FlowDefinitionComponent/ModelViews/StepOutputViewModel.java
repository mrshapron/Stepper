package Components.Main.FlowDefinitionComponent.ModelViews;

import Flow.Definition.FlowDefinition;
import Flow.Definition.StepUsageDeclaration;
import Mapping.MappingDataDefinition;
import Step.Declaration.DataDefinitionDeclaration;

import java.util.ArrayList;
import java.util.List;

public class StepOutputViewModel {
    private String name;
    private String connected;
    private String toInput;

    private static final String  freeOutputLabel = "Free Output";

    public StepOutputViewModel(FlowDefinition flowDefinition, StepUsageDeclaration stepUsageDeclaration, DataDefinitionDeclaration output){
        this.name = output.getAliasName();
        List<MappingDataDefinition> connectedMap = new ArrayList<>();
        for (MappingDataDefinition mappingDataDefinition : flowDefinition.getMappedDataDefinitions()) {
            if (mappingDataDefinition.getSourceData().getAliasName().equals(output.getAliasName()) && mappingDataDefinition.getSourceStep().equals(stepUsageDeclaration)) {
                connectedMap.add(mappingDataDefinition);
            }
        }

        if(!connectedMap.isEmpty()){
            MappingDataDefinition mapFound = connectedMap.get(0);
            this.connected = mapFound.getTargetStep().getFinalStepName();
            this.toInput = mapFound.getTargetData().getAliasName();
        }else{
            connected = freeOutputLabel;
            toInput = freeOutputLabel;
        }
    }

    public String getName() {
        return name;
    }

    public String getConnected() {
        return connected;
    }

    public String getToInput() {
        return toInput;
    }
}
