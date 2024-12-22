package nl.zwanepol.csp.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import nl.zwanepol.csp.exception.FileProcessingException;
import nl.zwanepol.csp.model.CustomerStatement;
import nl.zwanepol.csp.service.CsvService;
import nl.zwanepol.csp.service.StatementService;
import nl.zwanepol.csp.service.XmlService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("api/clientStatements")
public class StatementProcessor {

    @Resource
    private StatementService service;
    @Resource
    private CsvService csvService;
    @Resource
    private XmlService xmlService;

    @PostMapping("/process")
    public String processStatement(@RequestParam("file") MultipartFile file) {
        List<CustomerStatement> statements = parseFile(file);
        List<CustomerStatement> failedStatements = service.processStatements(statements);
        return generateReport(failedStatements);
    }

    private List<CustomerStatement> parseFile(MultipartFile file) {
        List<CustomerStatement> statements = new ArrayList<>();
        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null && originalFilename.endsWith(".csv")) {
                csvService.parseCsv(file.getInputStream(), statements);
            } else if (originalFilename != null && originalFilename.endsWith(".xml")) {
                xmlService.parseXml(file.getInputStream(), statements);
            }
        } catch (Exception e) {
            throw new FileProcessingException("Error processing file", e);
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
