package nl.zwanepol.csp.model;

import java.math.BigDecimal;

import nl.zwanepol.csp.util.LongAdapter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "statement_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "record")
public class CustomerStatement {
    @Id
    @Column(name = "REFERENCE")
    private Long reference;
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "START_BALANCE")
    private BigDecimal startBalance;
    @Column(name = "MUTATION")
    private BigDecimal mutation;
    @Column(name = "END_BALANCE")
    private BigDecimal endBalance;

    @XmlAttribute
    @XmlJavaTypeAdapter(LongAdapter.class)
    public Long getReference() {
        return reference;
    }
    @XmlElement
    public String getAccountNumber() {
        return accountNumber;
    }
    @XmlElement
    public String getDescription() {
        return description;
    }
    @XmlElement
    public BigDecimal getStartBalance() {
        return startBalance;
    }
    @XmlElement
    public BigDecimal getMutation() {
        return mutation;
    }
    @XmlElement
    public BigDecimal getEndBalance() {
        return endBalance;
    }
}
