package nl.zwanepol.csp.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "statements")
public class CustomerStatements {

    private List<CustomerStatement> statements;

    @XmlElement(name = "statement")
    public List<CustomerStatement> getStatements() {
        return statements;
    }

    public void setStatements(List<CustomerStatement> statements) {
        this.statements = statements;
    }
}