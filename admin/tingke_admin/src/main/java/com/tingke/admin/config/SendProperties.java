package com.tingke.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aliyun.send")
@Data
public class SendProperties {

    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
    private String verifyCodeTemplate;



}
