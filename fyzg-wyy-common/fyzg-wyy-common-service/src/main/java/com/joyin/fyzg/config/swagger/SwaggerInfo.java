package com.joyin.fyzg.config.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Swagger配置属性类
 * <br/>
 * @author pengzhen
 * @date 2019/7/23 0023 上午 9:45
 */
@Component
@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerInfo {

    private String groupName ="controller";

    private String basePackage;

    private boolean swaggerShow ;

    private String antPath;

    private String title = "HTTP API";

    private String description = "Swagger 自动生成接口文档";

    private String license = "Apache License Version 2.0";
}
