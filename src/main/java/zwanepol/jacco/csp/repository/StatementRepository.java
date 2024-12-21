package zwanepol.jacco.csp.repository;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import zwanepol.jacco.csp.model.CustomerStatement;

@Repository
public class StatementRepository {
    @Resource
    private JdbcTemplate jdbcTemplate;
    public Optional<CustomerStatement> findByReference(long reference) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM statement_records WHERE reference = ?",
                (rs, rowNum) -> new CustomerStatement(
                        rs.getLong("reference"),
                        rs.getString("accountNumber"),
                        rs.getString("description"),
                        rs.getBigDecimal("startBalance"),
                        rs.getBigDecimal("mutation"),
                        rs.getBigDecimal("endBalance")
                ), reference));
    }

    public void save(CustomerStatement statement) {
        jdbcTemplate.update("INSERT INTO statement_records "
                        + "(reference, accountNumber, description, startBalance, mutation, endBalance) "
                        + "VALUES (?, ?, ?, ?, ?, ?)",
                statement.getReference(),
                statement.getAccountNumber(),
                statement.getDescription(),
                statement.getStartBalance(),
                statement.getMutation(),
                statement.getEndBalance());
    }
}
