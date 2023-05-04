package Stepper.Input.Read;

import Stepper.JAXB.Generated.STFlow;
import Stepper.Step.Declaration.DataDefinitionDeclaration;

import java.util.List;

public interface ReaderUser {
    List<STFlow> readXMLFile(String xmlFilePath);
}
