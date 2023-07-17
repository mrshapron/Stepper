package Input.Read;


import JAXB.Generated.STFlow;
import JAXB.Generated.STStepper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public interface ReaderUser {
    STStepper readXMLFile(String xmlFilePath);

    STStepper readXMLFileViaFile(InputStream fileContent);
}
