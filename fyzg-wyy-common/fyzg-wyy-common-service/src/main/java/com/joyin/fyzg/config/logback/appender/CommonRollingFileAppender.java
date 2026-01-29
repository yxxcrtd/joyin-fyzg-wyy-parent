package com.joyin.fyzg.config.logback.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义日志逻辑
 * <br/>
 *
 * @author pidong
 * @date 2022/3/15 11:48
 */
public class CommonRollingFileAppender extends RollingFileAppender<ILoggingEvent> {

    @Override
    public void setFile(String file) {
        DateTimeFormatter yyyyMMdd_EN = DateTimeFormatter.ofPattern("yyyyMMdd");
        try {
            if(file.contains("%d{yyyyMMdd}")){
                file = file.replaceAll("%d\\{yyyyMMdd}", LocalDateTime.now().format(yyyyMMdd_EN));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.setFile(file);
    }

}
