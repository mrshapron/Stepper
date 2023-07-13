package Input.Read;

import JAXB.Generated.STFlow;
import JAXB.Generated.STStepper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class FlowReaderXML implements ReaderUser {
    private final String JAXB_PACKAGE_NAME = "JAXB.Generated";
    @Override
    public STStepper readXMLFile(String xmlFilePath) {
        try {
            InputStream inputStream = new FileInputStream(xmlFilePath);
            STStepper stepper = deserializeFrom(inputStream, JAXB_PACKAGE_NAME);
            return stepper;
        } catch (FileNotFoundException e) {
            return null;
        } catch (JAXBException e) {
            return null;
        }
    }

    @Override
    public STStepper readXMLFileViaFile(InputStream fileContent) {
        try {
            STStepper stepper = deserializeFrom(fileContent, JAXB_PACKAGE_NAME);
            return stepper;
        } catch (JAXBException e) {
            return null;
        }
    }

    private STStepper deserializeFrom(InputStream in, String jaxbPackage) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(jaxbPackage);
        Unmarshaller u = jc.createUnmarshaller();
        return (STStepper) u.unmarshal(in);
    }
}
