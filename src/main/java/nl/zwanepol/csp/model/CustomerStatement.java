package nl.zwanepol.csp.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "statement_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerStatement {
    @Id
    @Column(name = "REFERENCE")
    private long reference;
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
}
