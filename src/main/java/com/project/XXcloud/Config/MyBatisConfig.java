package com.project.XXcloud.Config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 */
@Configuration
@MapperScan("com.project.XXcloud.Mbg.mapper")
public class MyBatisConfig {
}
