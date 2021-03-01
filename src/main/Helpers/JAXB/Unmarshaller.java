package Helpers.JAXB;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.Serializable;
import java.io.StringReader;

public class Unmarshaller {

    /**
     * Génère une classe instanciée à partir d'un XML/JSON
     * @param xmlPath
     * @param objectClass
     * @return
     * @throws JAXBException
     */
    public static Serializable unmarshall(String xmlPath, Class objectClass) throws JAXBException {

        File xmlFile = new File(xmlPath);
        JAXBContext jaxbContext = JAXBContext.newInstance(objectClass);
        javax.xml.bind.Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Serializable result = (Serializable) jaxbUnmarshaller.unmarshal(xmlFile);
        return result;
    }

    /**
     * Génère une classe instanciée à partir d'un XML/JSON
     * @param xmlPath
     * @param objectClass
     * @return
     * @throws JAXBException
     */
    public static Serializable unmarshallFromString(String value, Class objectClass) throws JAXBException {

        StreamSource ss = new StreamSource(new StringReader(value));
        JAXBContext jaxbContext = JAXBContext.newInstance(objectClass);
        javax.xml.bind.Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Serializable result = (Serializable) jaxbUnmarshaller.unmarshal(ss);
        return result;
    }
}
