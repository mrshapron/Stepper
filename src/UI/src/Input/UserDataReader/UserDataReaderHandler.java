package Input.UserDataReader;


import Flow.Defenition.FlowDefinition;
import Flow.Defenition.FreeInputsDefinition;

import java.util.List;
import java.util.Map;

public interface UserDataReaderHandler {
    List<FlowDefinition> ReadUserFlowInput();
    Map<String, Object> ReadDataInput(List<FreeInputsDefinition> freeInputsDefDec);
}
