package Input;


import Flow.Defenition.FlowDefinition;
import Step.Declaration.DataDefinitionDeclaration;

import java.util.List;

public interface InitializerData {
    List<FlowDefinition> InitializeFlows(String filePath);
    Object InitializeData(DataDefinitionDeclaration dataDefinitionDeclaration);
}
