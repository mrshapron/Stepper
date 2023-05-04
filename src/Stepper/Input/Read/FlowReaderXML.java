package Stepper.Input.Read;

import Stepper.JAXB.Generated.STFlow;
import Stepper.JAXB.Generated.STStepper;
import Stepper.Step.Declaration.DataDefinitionDeclaration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class FlowReaderXML implements ReaderUser {
    private final String JAXB_PACKAGE_NAME = "Stepper.JAXB.Generated";
    @Override
    public List<STFlow> readXMLFile(String xmlFilePath) {
        try {
            InputStream inputStream = new FileInputStream(xmlFilePath);
            STStepper stepper = deserializeFrom(inputStream, xmlFilePath, JAXB_PACKAGE_NAME);
            return stepper.getSTFlows().getSTFlow();
        } catch (FileNotFoundException e) {
            System.out.println("XML File Not Found..");
            return null;
        } catch (JAXBException e) {
            System.out.println("XML File Not Found..");
            return null;
        }
    }

    private STStepper deserializeFrom(InputStream in, String filePath, String jaxbPackage) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(jaxbPackage);
        Unmarshaller u = jc.createUnmarshaller();
        return (STStepper) u.unmarshal(in);
    }
}
