package nl.zwanepol.csp.service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<CustomerStatement> processStatements(List<CustomerStatement> statements) {
        Set<Long> references = new HashSet<>();
        List<CustomerStatement> failedStatements = new ArrayList<>();
        for (CustomerStatement statement : statements) {
            if (isValid(statement, references)) {
                references.add(statement.getReference());
                repository.save(statement);
            }
            else {
                failedStatements.add(statement);
            }
        }
        return failedStatements;
    }

    private boolean isValid(CustomerStatement statement, Set<Long> references) {
        log.info("validating statement: {}, {}", statement.getEndBalance(), calculateEndBalance(statement));
        return statement.getEndBalance().equals(calculateEndBalance(statement))
                && isUniqueReference(statement.getReference(), references)
                && isNotSaved(statement.getReference());
    }

    private BigDecimal calculateEndBalance(CustomerStatement statement) {
        return statement.getStartBalance().add(statement.getMutation());
    }

    private boolean isUniqueReference(long reference, Set<Long> references) {
        return !references.contains(reference);
    }

    private boolean isNotSaved(long reference) {
        return repository.findByReference(reference).isEmpty();
    }
}

