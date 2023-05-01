package Stepper.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LoggerImpl implements Logger{
    private static Logger instance = null;
    private final List<Log> logs;
    private final List<Log> summaries;

    private LoggerImpl() {
        // private constructor to prevent instantiation
        logs = new ArrayList<>();
        summaries = new ArrayList<>();
    }

    public void addLog(String log) {
        // implementation for adding new log message to list with timestamp
        LocalDateTime now = LocalDateTime.now();
        Log newLog = new Log(now, log);
        System.out.println(newLog); // For Debugging
        logs.add(newLog);
    }

    public void addSummaryLine(String summary) {
        // implementation for adding new summary line to list with timestamp
        LocalDateTime now = LocalDateTime.now();
        Log newSummary = new Log(now, summary);

        System.out.println(newSummary); // For Debugging
        summaries.add(newSummary);
    }

    public List<Log> getLogs() {
        // return the list of logs
        return logs;
    }

    public List<Log> getSummaries() {
        // return the list of summaries
        return summaries;
    }

    public static Logger getInstance() {
        if (instance == null) {
            synchronized(Logger.class) {
                if (instance == null) {
                    instance = new LoggerImpl();
                }
            }
        }
        return instance;
    }
}
