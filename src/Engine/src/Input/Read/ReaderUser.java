package Input.Read;


import JAXB.Generated.STFlow;
import JAXB.Generated.STStepper;

import java.util.List;

public interface ReaderUser {
    STStepper readXMLFile(String xmlFilePath);
}
