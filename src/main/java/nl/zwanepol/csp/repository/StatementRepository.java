package nl.zwanepol.csp.repository;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import nl.zwanepol.csp.model.CustomerStatement;

@Repository
public class StatementRepository {
    @Resource
    private JdbcTemplate jdbcTemplate;
    public Optional<CustomerStatement> findByReference(long reference) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM statement_records WHERE reference = ?",
                (rs, rowNum) -> new CustomerStatement(
                        rs.getLong("REFERENCE"),
                        rs.getString("ACCOUNT_NUMBER"),
                        rs.getString("DESCRIPTION"),
                        rs.getBigDecimal("START_BALANCE"),
                        rs.getBigDecimal("MUTATION"),
                        rs.getBigDecimal("END_BALANCE")
                ), reference));
    }

    public void save(CustomerStatement statement) {
        jdbcTemplate.update("INSERT INTO statement_records "
                        + "(REFERENCE, ACCOUNT_NUMBER, DESCRIPTION, START_BALANCE, MUTATION, END_BALANCE) "
                        + "VALUES (?, ?, ?, ?, ?, ?)",
                statement.getReference(),
                statement.getAccountNumber(),
                statement.getDescription(),
                statement.getStartBalance(),
                statement.getMutation(),
                statement.getEndBalance());
    }
}
