package nl.zwanepol.csp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.xml.bind.JAXBException;
import nl.zwanepol.csp.model.CustomerStatement;
import nl.zwanepol.csp.service.CsvService;
import nl.zwanepol.csp.service.StatementService;
import nl.zwanepol.csp.service.XmlService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.exceptions.CsvException;

@RestController
@RequestMapping("api/clientStatements")
public class StatementProcessor {

    @Resource
    private StatementService service;
    @Resource
    private CsvService csvService;
    @Resource
    private XmlService xmlService;
    private final List<CustomerStatement> statements = new ArrayList<>();
    private final List<CustomerStatement> failedStatements = new ArrayList<>();

    @PostMapping("/process")
    public String processStatement(@RequestParam("file") MultipartFile file) {
        List<CustomerStatement> records = parseFile(file);
        service.processStatements(records);
        return generateReport(failedStatements);
    }

    private List<CustomerStatement> parseFile(MultipartFile file) {

        try {
            if (file.getOriginalFilename().endsWith(".csv")) {
                csvService.parseCsv(file.getInputStream(), statements, failedStatements);
            } else if (file.getOriginalFilename().endsWith(".xml")) {
                xmlService.parseXml(file.getInputStream(), statements, failedStatements);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return statements;
    }

    private String generateReport(List<CustomerStatement> failedStatements) {
        StringBuilder report = new StringBuilder("Failed Records Report:\n");
        for (CustomerStatement statement : failedStatements) {
            report.append("Reference: ").append(statement.getReference())
                    .append(", Description: ").append(statement.getDescription()).append("\n");
        }
        return report.toString();
    }


}
