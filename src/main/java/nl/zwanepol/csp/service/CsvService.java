package nl.zwanepol.csp.service;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import nl.zwanepol.csp.model.CustomerStatement;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CsvService {
    public void parseCsv(InputStream input,
                         List<CustomerStatement> statements)
            throws IOException, CsvException {
        CSVReader reader = new CSVReader(new InputStreamReader(input));
        if (reader.readNext()[0].equalsIgnoreCase("reference")) {
            log.info("Skipping header");
        }
        List<String[]> records = reader.readAll();
        for (String[] line : records) {
            CustomerStatement statement = new CustomerStatement(
                    Long.parseLong(line[0]),
                    line[1],
                    line[2],
                    new BigDecimal(line[3]),
                    new BigDecimal(line[4]),
                    new BigDecimal(line[5])
            );
            statements.add(statement);
        }
    }
}
