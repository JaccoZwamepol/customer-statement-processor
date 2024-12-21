package zwanepol.jacco.csp.service;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zwanepol.jacco.csp.model.CustomerStatement;
import zwanepol.jacco.csp.repository.StatementRepository;

@Service
public class SatementService {
    private StatementRepository repository;

    @Autowired
    public void processStatements(List<CustomerStatement> statements) {
        for (CustomerStatement statement : statements) {
            if (isValid(statement)) {
                repository.save(statement);
            }
        }
    }

    private boolean isValid(CustomerStatement statement) {
        return repository.findByReference(statement.getReference()).isEmpty()
                && statement.getEndBalance().equals(calculateEndBalance(statement));
    }

    private BigDecimal calculateEndBalance(CustomerStatement statement) {
        return statement.getStartBalance().add(statement.getMutation()).multiply(statement.getEndBalance());
    }
}

