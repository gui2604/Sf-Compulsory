package br.com.fiap.api.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogSummaryService {

    public static class LogEntry {
        public String level;
        public String message;
        public LocalDateTime timestamp;

        public LogEntry() {}  // Necessário para Jackson

        public LogEntry(String level, String message) {
            this.level = level;
            this.message = message;
            this.timestamp = LocalDateTime.now();
        }
    }

    private final List<LogEntry> entries = new ArrayList<>();
    private final File file;
    private final ObjectMapper objectMapper;

    public LogSummaryService() {
        // Cria diretório logs se não existir
        File dir = new File("logs");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        this.file = new File(dir, "logs-summary.json");

        // Configura ObjectMapper com suporte a java.time.*
        objectMapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .registerModule(new JavaTimeModule());

        loadLogsFromFile();
    }

    public void addLog(String level, String message) {
        LogEntry entry = new LogEntry(level, message);
        entries.add(entry);
        writeToFile();
    }

    public List<LogEntry> getSummary() {
        return entries;
    }

    private void writeToFile() {
        try {
            objectMapper.writeValue(file, entries);
        } catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo de log JSON: " + e.getMessage());
        }
    }

    private void loadLogsFromFile() {
        if (file.exists()) {
            try {
                LogEntry[] saved = objectMapper.readValue(file, LogEntry[].class);
                entries.addAll(List.of(saved));
            } catch (IOException e) {
                System.err.println("Erro ao carregar logs existentes: " + e.getMessage());
            }
        }
    }
}
