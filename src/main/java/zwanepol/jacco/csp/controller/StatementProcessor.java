package zwanepol.jacco.csp.controller;

import java.util.List;

import zwanepol.jacco.csp.model.CustomerStatement;
import zwanepol.jacco.csp.service.SatementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/clientStatements")
public class StatementProcessor {
    private SatementService service;
    @Autowired
    @PostMapping("/process")
    public String processStatement(@RequestParam("file") MultipartFile file) {
        List<CustomerStatement> statements = parseFile(file);
        service.processStatements(statements);
        return "Statement processed successfully";
    }

    private List<CustomerStatement> parseFile(MultipartFile file) {
        return List.of();
    }
}
