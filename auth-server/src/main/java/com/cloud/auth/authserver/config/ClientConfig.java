package com.cloud.auth.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * clientService相关配置
 */
@Configuration
public class ClientConfig {

    @Autowired
    private DataSource dataSource;

    @Bean("customJdbcClientDetailsService")
    public CustomJdbcClientDetailsService getCustomJdbcClientDetailsService(DataSource dataSource){
        return new CustomJdbcClientDetailsService(dataSource);
    }

    /**
     * 自定义Jdbc获取client
     * 可以通过setSelectClientDetailsSql修改默认sql
     */
    class CustomJdbcClientDetailsService extends JdbcClientDetailsService {

        public CustomJdbcClientDetailsService(DataSource dataSource) {
            super(dataSource);
        }

    }
}
