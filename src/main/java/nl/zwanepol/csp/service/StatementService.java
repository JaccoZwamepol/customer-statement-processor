package nl.zwanepol.csp.service;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import nl.zwanepol.csp.model.CustomerStatement;
import nl.zwanepol.csp.repository.StatementRepository;

@Slf4j
@Service
public class StatementService {
    @Resource
    private StatementRepository repository;


    public void processStatements(List<CustomerStatement> statements) {
        for (CustomerStatement statement : statements) {
            if (isValid(statement)) {
                repository.save(statement);
            }
        }
    }

    private boolean isValid(CustomerStatement statement) {
        log.info("validating statement: {}, {}", statement.getEndBalance(), calculateEndBalance(statement));
        return statement.getEndBalance().equals(calculateEndBalance(statement));
    }

    private BigDecimal calculateEndBalance(CustomerStatement statement) {
        return statement.getStartBalance().add(statement.getMutation());
    }
}

