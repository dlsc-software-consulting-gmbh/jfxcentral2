package com.dlsc.jfxcentral2.app.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.OutputStream;

public class LoggerOutputStream extends OutputStream {

    private final Logger logger;
    private final Level level;
    private final StringBuilder sb = new StringBuilder();

    public LoggerOutputStream(Logger logger, Level level) {
        this.logger = logger;
        this.level = level;
    }

    @Override
    public void write(int b) {
        char ch = (char) b;
        if (Character.toString(ch).equals(System.getProperty("line.separator"))) {
            logger.log(level, sb.toString());
            sb.delete(0, sb.toString().length());
        } else {
            sb.append(ch);
        }
    }
}
