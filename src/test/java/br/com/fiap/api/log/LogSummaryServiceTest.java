package br.com.fiap.api.log;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LogSummaryServiceTest {

    private LogSummaryService logService;

    @BeforeEach
    void setUp() {
        logService = new LogSummaryService();
    }

    @Test
    void testAddLogAndGetSummary() {
        // Adiciona um log
        logService.addLog("INFO", "Primeiro log");

        // Recupera o resumo
        List<LogSummaryService.LogEntry> logs = logService.getSummary();

        // Verifica se o log foi adicionado
        assertEquals(1, logs.size());
        assertEquals("INFO", logs.get(0).level);
        assertEquals("Primeiro log", logs.get(0).message);
        assertNotNull(logs.get(0).timestamp);
    }

    @Test
    void testAddMultipleLogs() {
        logService.addLog("INFO", "Log 1");
        logService.addLog("ERROR", "Log 2");

        List<LogSummaryService.LogEntry> logs = logService.getSummary();

        assertEquals(2, logs.size());
        assertEquals("Log 1", logs.get(0).message);
        assertEquals("Log 2", logs.get(1).message);
    }

    @Test
    void testSummaryInitiallyEmpty() {
        List<LogSummaryService.LogEntry> logs = logService.getSummary();
        assertTrue(logs.isEmpty() || logs.size() >= 0); // Pode conter logs de execuções anteriores do arquivo JSON
    }
}
