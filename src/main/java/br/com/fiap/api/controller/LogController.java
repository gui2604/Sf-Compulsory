package br.com.fiap.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.api.log.LogSummaryService;
import br.com.fiap.api.log.LogSummaryService.LogEntry;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    private LogSummaryService logSummaryService;

    @GetMapping("/summary")
    public List<LogEntry> getLogsSummary() {
        return logSummaryService.getSummary();
    }
}
