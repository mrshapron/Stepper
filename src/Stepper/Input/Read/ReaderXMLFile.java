package Stepper.Input.Read;

import Stepper.JAXB.Generated.STFlow;

import java.util.List;

public interface ReaderXMLFile {
    List<STFlow> read(String xmlFilePath);
}
