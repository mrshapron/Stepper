package Convert;


import ContinuationPac.ContinuationMetaData;
import Flow.Definition.FlowDefinition;
import JAXB.Generated.STFlow;
import JAXB.Generated.STStepper;

import java.util.List;

public interface FlowConverter {
    FlowDefinition Convert(STFlow flowJAXB);
    List<ContinuationMetaData> ConvertContinuations(STStepper stStepper);
}