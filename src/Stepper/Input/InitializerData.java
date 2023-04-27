package Stepper.Input;

import Stepper.Flow.Defenition.FlowDefinition;

import java.util.List;

public interface InitializerData {
    List<FlowDefinition> InitializeFlows(String filePath);
}
