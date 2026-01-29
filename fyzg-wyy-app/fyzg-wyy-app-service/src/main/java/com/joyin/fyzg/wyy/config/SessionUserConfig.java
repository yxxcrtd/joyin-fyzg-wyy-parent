package com.joyin.fyzg.wyy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "session-user")
@Data
public class SessionUserConfig {

    private List<Map<String,Object>> sqlList;

    private List<Map<String,Object>> keyList;
}
