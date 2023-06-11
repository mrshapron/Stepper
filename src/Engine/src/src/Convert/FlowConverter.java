package src.Convert;


import Flow.Defenition.FlowDefinition;
import JAXB.Generated.STFlow;

public interface FlowConverter {
    FlowDefinition Convert(STFlow flowJAXB);
}
