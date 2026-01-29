package com.joyin.fyzg.config.redis;

import com.alibaba.fastjson.JSON;
import com.joyin.fyzg.utils.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class JedisConfig {

    @Autowired
    private RedisProperties redisPropertie;

    @Bean
    public JedisUtil initRedisBean(){
        JedisUtil redis = new JedisUtil(redisPropertie);
        log.info(JSON.toJSONString(redisPropertie));
        log.info("------------【注入JedisUtil成功！】------------------");
        return redis;
    }
}
