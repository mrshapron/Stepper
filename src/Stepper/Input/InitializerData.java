package Stepper.Input;

import Stepper.Flow.Defenition.FlowDefinition;
import Stepper.Step.Declaration.DataDefinitionDeclaration;

import java.util.List;

public interface InitializerData {
    List<FlowDefinition> InitializeFlows(String filePath);
    Object InitializeData(DataDefinitionDeclaration dataDefinitionDeclaration);
}
