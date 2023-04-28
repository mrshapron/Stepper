package Stepper.Log;

import java.time.LocalDateTime;
import java.util.List;

public interface Logger {
    void addNewLog(String log);
    void addNewSummaryLine(String summary);
    List<Log> getLogs();
    List<Log> getSummaries();

    public static class Log {
        private final LocalDateTime time;
        private final String message;

        public Log(LocalDateTime time, String message) {
            this.time = time;
            this.message = message;
        }

        public LocalDateTime getTime() {
            return time;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "[" + time + "] " + message;
        }
    }
}
