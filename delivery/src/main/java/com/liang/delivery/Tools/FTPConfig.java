package com.liang.delivery.Tools;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(FTPServer.class)
public class FTPConfig {
    @Resource
    private FTPServer ftpServer;

    @Bean
    @ConditionalOnMissingBean
    public FtpClient getFtpClient(){
        FtpClient ftpClient =  new FtpClient(ftpServer);
        return ftpClient;
    }
}
