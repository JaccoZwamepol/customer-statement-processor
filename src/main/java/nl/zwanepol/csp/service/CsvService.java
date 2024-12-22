package nl.zwanepol.csp.service;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import nl.zwanepol.csp.model.CustomerStatement;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CsvService {
    public void parseCsv(InputStream input,
                         List<CustomerStatement> statements,
                         List<CustomerStatement> failedStatements)
            throws IOException, CsvException {
        CSVReader reader = new CSVReader(new InputStreamReader(input));
        if (reader.readNext()[0].equalsIgnoreCase("reference")) {
            log.info("Skipping header");
        }
        List<String[]> records = reader.readAll();
        Set<Long> references = new HashSet<>();
        for (String[] line : records) {
            long reference = Long.parseLong(line[0]);
            references.add(reference);
            CustomerStatement statement = new CustomerStatement(
                    Long.parseLong(line[0]),
                    line[1],
                    line[2],
                    new BigDecimal(line[3]),
                    new BigDecimal(line[4]),
                    new BigDecimal(line[5])
            );
            if (references.contains(reference)) {
                failedStatements.add(statement);
            }
            else {
                statements.add(statement);
            }
        }
    }
}
