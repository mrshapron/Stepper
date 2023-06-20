package Components.Main.FlowDefinitionComponent.ModelViews;

import Flow.Definition.FlowDefinition;
import Flow.Definition.StepUsageDeclaration;
import Mapping.MappingDataDefinition;
import Step.Declaration.DataDefinitionDeclaration;
import javafx.beans.property.ReadOnlyStringWrapper;

import java.util.ArrayList;
import java.util.List;

public class StepInputsViewModel {
    private String name;
    private String necessity;
    private String connected;
    private String fromOutput;

    private ReadOnlyStringWrapper nameProperty;
    private ReadOnlyStringWrapper necessityProperty;
    private ReadOnlyStringWrapper connectedProperty;
    private ReadOnlyStringWrapper fromOutputProperty;

    private static final String  freeInputLabel = "Free Input";

    public StepInputsViewModel(FlowDefinition flowDefinition, StepUsageDeclaration stepUsageDeclaration, DataDefinitionDeclaration input){
        this.name = input.getAliasName();
        this.necessity = input.necessity().name();
        List<MappingDataDefinition> connectedMap = new ArrayList<>();
        for (MappingDataDefinition mappingDataDefinition : flowDefinition.getMappedDataDefinitions()) {
            if (mappingDataDefinition.getTargetData().getAliasName().equals(input.getAliasName()) && mappingDataDefinition.getTargetStep().equals(stepUsageDeclaration)) {
                connectedMap.add(mappingDataDefinition);
            }
        }
        if(!connectedMap.isEmpty()){
            MappingDataDefinition mapFound = connectedMap.get(0);
             this.connected = mapFound.getSourceStep().getFinalStepName();
             this.fromOutput = mapFound.getSourceData().getAliasName();
        }else{
            connected = freeInputLabel;
            fromOutput = freeInputLabel;
        }


        nameProperty = new ReadOnlyStringWrapper(name);
        necessityProperty = new ReadOnlyStringWrapper(necessity);
        connectedProperty = new ReadOnlyStringWrapper(connected);
        fromOutputProperty = new ReadOnlyStringWrapper(fromOutput);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNecessity() {
        return necessity;
    }

    public void setNecessity(String necessity) {
        this.necessity = necessity;
    }

    public String getConnected() {
        return connected;
    }

    public void setConnected(String connected) {
        this.connected = connected;
    }

    public String getFromOutput() {
        return fromOutput;
    }

    public void setFromOutput(String fromOutput) {
        this.fromOutput = fromOutput;
    }
}
