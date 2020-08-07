package com.tingke.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "send.email.qq")
@Data
public class SendEmailProperties {

    private String smtpHost;
    private String from;
    private String personalName;
    private String checkCode;

}
