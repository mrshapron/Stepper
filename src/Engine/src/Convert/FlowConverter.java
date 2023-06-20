package Convert;


import ContinuationPac.ContinuationMetaData;
import ContinuationPac.FlowSourceTarget;
import Flow.Definition.FlowDefinition;
import JAXB.Generated.STFlow;
import JAXB.Generated.STStepper;

import java.util.List;
import java.util.Map;

public interface FlowConverter {
    FlowDefinition Convert(STFlow flowJAXB);
    List<ContinuationMetaData> ConvertContinuations(STStepper stStepper);
}