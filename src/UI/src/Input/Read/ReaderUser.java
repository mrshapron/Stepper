package Input.Read;


import JAXB.Generated.STFlow;

import java.util.List;

public interface ReaderUser {
    List<STFlow> readXMLFile(String xmlFilePath);
}
