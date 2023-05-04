package Stepper.Input.UserDataReader;

import Stepper.Flow.Defenition.FlowDefinition;
import Stepper.Flow.Defenition.FreeInputsDefinition;
import Stepper.Step.Declaration.DataDefinitionDeclaration;

import java.util.List;
import java.util.Map;

public interface UserDataReaderHandler {
    List<FlowDefinition> ReadUserFlowInput();
    Map<String, Object> ReadDataInput(List<FreeInputsDefinition> freeInputsDefDec);
}
