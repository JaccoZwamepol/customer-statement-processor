package nl.zwanepol.csp.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "records")
public class Records {

    private List<CustomerStatement> customerStatements;

    @XmlElement(name = "record")
    public List<CustomerStatement> getCustomerStatements() {
        return customerStatements;
    }

    public void setCustomerStatements(List<CustomerStatement> customerStatements) {
        this.customerStatements = customerStatements;
    }
}