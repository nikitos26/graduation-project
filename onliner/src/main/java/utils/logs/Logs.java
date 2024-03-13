package utils.logs;

import lombok.extern.log4j.Log4j;

import java.io.File;

@Log4j
public class Logs {

    public static void clearLogDirectory() {
        log.info("Start clearing logs directory.");
        String logDirectoryPath = "./target/logs/";
        File logDirectory = new File(logDirectoryPath);

        if (logDirectory.exists() && logDirectory.isDirectory()) {
            File[] logFiles = logDirectory.listFiles();
            if (logFiles != null) {
                for (File file : logFiles) {
                    file.delete();
                }
            }
        }
        log.info("Logs directory was cleared.");
    }
}
