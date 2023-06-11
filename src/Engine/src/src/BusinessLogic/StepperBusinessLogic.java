package src.BusinessLogic;

import Flow.Defenition.FlowDefinition;

import java.util.List;

public interface StepperBusinessLogic {
    List<FlowDefinition> initializeFlowsList(String absolutePath);
}
