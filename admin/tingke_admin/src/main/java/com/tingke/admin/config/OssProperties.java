package com.tingke.admin.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aliyun.oss.file")
@Data
public class OssProperties {

    private String endpoint;
    private String accessId;
    private String accessKey;
    private String bucket;

}
