package com.joyin.fyzg.wyy;

import com.joyin.fyzg.config.modelmapper.ModelMapperConfig;
import com.joyin.fyzg.config.redis.JedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


@Slf4j
@SpringBootApplication
@ComponentScan({ "com.joyin.fyzg" })
@MapperScan(basePackages = {
        "com.joyin.fyzg.wyy.mapper.**.*",
        "com.joyin.fyzg.wyy.mapper.**",
})
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@Import(value = {  ModelMapperConfig.class,JedisConfig.class})
public class WyyAppApplication {

    static {
        SSLUtil.disableSSLVerfication();
    }

    public static void main(String[] args) {
        try{
            SpringApplication.run(WyyAppApplication.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
