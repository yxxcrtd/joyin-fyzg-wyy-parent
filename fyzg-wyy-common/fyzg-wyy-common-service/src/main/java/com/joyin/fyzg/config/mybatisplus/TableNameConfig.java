package com.joyin.fyzg.config.mybatisplus;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "table-name")
@Data
public class TableNameConfig {

    List<Map<String, String>> nameList;

    private  final String EN_NAME = "enName";
    private  final String FULL_NAME = "fullName";
    private  final String CN_NAME = "cnName";

    public String getFullNameByEnName(String enName) {
        if (null != this.getNameList()) {
            return this.getNameList().stream()
                    .filter(table -> table.get(EN_NAME).equals(enName))
                    .findFirst()
                    .map(table->table.get(FULL_NAME))
                    .orElse(enName);
        } else {
            return enName;
        }
    }

    public String getCnNameByEnName(String enName) {
        if (null != this.getNameList()) {
            return this.getNameList().stream()
                    .filter(table -> table.get(EN_NAME).equals(enName))
                    .findFirst()
                    .map(table->table.get(CN_NAME))
                    .orElse(enName);
        } else {
            return enName;
        }
    }
}
