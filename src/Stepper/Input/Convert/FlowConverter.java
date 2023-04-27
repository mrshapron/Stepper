package Stepper.Input.Convert;

import Stepper.JAXB.Generated.STFlow;
import Stepper.Flow.Defenition.FlowDefinition;

public interface FlowConverter {
    FlowDefinition Convert(STFlow flowJAXB);
}
