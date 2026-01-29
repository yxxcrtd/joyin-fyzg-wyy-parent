package com.joyin.fyzg.config.redis;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(
        prefix = "redis"
)
public class RedisProperties {

    public static final String TYPE_ALONE = "alone";
    public static final String TYPE_SENTINEL = "sentinel";
    public static final String TYPE_CLUSTER = "cluster";

    public List<String> redisSentinels;

    public String masterName;

    //alone sentinel cluster
    public String redisType;

    public String aloneIp;

    public Integer alonePort;

    public String passWord;

}
