import java.io.FileWriter;
import java.io.IOException;

public enum logLevel{
  DEBUG, ERROR, INFO
}
public abstract class LogProcessor {
    protected LogProcessor nextLogProcessor;
    protected LogSink sink;

    public LogProcessor(LogProcessor nextLogProcessor, LogSink sink) {
        this.nextLogProcessor = nextLogProcessor;
        this.sink = sink;
    }

    public void log(int logLevel, String message) {
        if (nextLogProcessor != null) {
            nextLogProcessor.log(logLevel, message);
        }
    }
}


interface LogSink {
    void writeLog(String formattedLog);
}


class ConsoleSink implements LogSink {
    @Override
    public void writeLog(String formattedLog) {
        System.out.println(formattedLog);
    }
}


class FileSink implements LogSink {
    private String logFilePath = "application.log";

    @Override
    public void writeLog(String formattedLog) {
        try (FileWriter writer = new FileWriter(logFilePath, true)) {
            writer.write(formattedLog + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class InfoLogProcessor extends LogProcessor {
    public InfoLogProcessor(LogProcessor nextLogProcessor, LogSink sink) {
        super(nextLogProcessor, sink);
    }

    public void log(int logLevel, String message) {
        if (logLevel == INFO) {
            String formattedMessage = "INFO: " + message;
            sink.writeLog(formattedMessage);
        } else if (nextLogProcessor != null) {
            nextLogProcessor.log(logLevel, message);
        }
    }
}


class ErrorLogProcessor extends LogProcessor {
    public ErrorLogProcessor(LogProcessor nextLogProcessor, LogSink sink) {
        super(nextLogProcessor, sink);
    }

    public void log(int logLevel, String message) {
        if (logLevel == ERROR) {
            String formattedMessage = "ERROR: " + message;
            sink.writeLog(formattedMessage);
        } else if (nextLogProcessor != null) {
            nextLogProcessor.log(logLevel, message);
        }
    }
}


class DebugLogProcessor extends LogProcessor {
    public DebugLogProcessor(LogProcessor nextLogProcessor, LogSink sink) {
        super(nextLogProcessor, sink);
    }

    public void log(int logLevel, String message) {
        if (logLevel == DEBUG) {
            String formattedMessage = "DEBUG: " + message;
            sink.writeLog(formattedMessage);
        } else if (nextLogProcessor != null) {
            nextLogProcessor.log(logLevel, message);
        }
    }
}

// 8️⃣ Main (Client)
public class Main {
    public static void main(String[] args) {
        // Define log sinks (you can use console or file sink)
        LogSink consoleSink = new ConsoleSink();
        LogSink fileSink = new FileSink();

        // Build the Chain of Responsibility
        LogProcessor logProcessorChain = new InfoLogProcessor(new ErrorLogProcessor(new DebugLogProcessor(null, fileSink),consoleSink),consoleSink);

        // Logs
        logProcessorChain.log(LogProcessor.ERROR, "This is an error message.");
        logProcessorChain.log(LogProcessor.INFO, "This is an info message.");
        logProcessorChain.log(LogProcessor.DEBUG, "This is a debug message.");
    }
}
