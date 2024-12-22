package nl.zwanepol.csp.service;
import java.io.InputStream;
import java.util.List;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;

import nl.zwanepol.csp.model.CustomerStatement;
import nl.zwanepol.csp.model.Records;

import org.springframework.stereotype.Service;

@Slf4j
@Service
public class XmlService {
    public void parseXml(InputStream input,
                         List<CustomerStatement> statements)
            throws JAXBException {
        log.info("Parsing XML file");
        JAXBContext context = JAXBContext.newInstance(Records.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Records customerStatements = (Records) unmarshaller.unmarshal(input);
        statements.addAll(customerStatements.getCustomerStatements());
    }
}
