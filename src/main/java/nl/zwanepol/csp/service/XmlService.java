package nl.zwanepol.csp.service;
import java.io.InputStream;
import java.util.List;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import jakarta.xml.bind.JAXBException;
import nl.zwanepol.csp.model.CustomerStatement;
import nl.zwanepol.csp.model.CustomerStatements;

import org.springframework.stereotype.Service;

@Service
public class XmlService {
    public void parseXml(InputStream input,
                         List<CustomerStatement> statements,
                         List<CustomerStatement> duplicateReferences)
            throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(CustomerStatements.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        CustomerStatements customerStatements = (CustomerStatements) unmarshaller.unmarshal(input);
        statements.addAll(customerStatements.getStatements());
    }
}
