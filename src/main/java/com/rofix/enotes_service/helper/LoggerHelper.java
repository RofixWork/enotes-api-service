package com.rofix.enotes_service.helper;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoggerHelper {
    public void createLog(Level level, String source, String method, String message, Object... args)
    {
        log.makeLoggingEventBuilder(level).log("[{}] :: [{}] :: {}", source, method, message, args);
    }
}
