package com.joyin.fyzg.wyy.config.cas.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "file.upload")
public class FileUploadProperties {
    /**
     * 文件存储根路径
     */
    private String basePath;

    /**
     * 文件访问 URI 前缀
     */
    private String accessUri;

}
