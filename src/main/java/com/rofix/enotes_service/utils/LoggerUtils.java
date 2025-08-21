package com.rofix.enotes_service.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;

@Slf4j
public class LoggerUtils {
    public static void createLog(Level level, String source, String method, String message, Object... args)
    {
        log.makeLoggingEventBuilder(level).log("[{}] :: [{}] :: {}", source, method, message, args);
    }
}
