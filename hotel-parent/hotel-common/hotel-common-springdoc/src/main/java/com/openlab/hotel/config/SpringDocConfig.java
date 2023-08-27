package com.openlab.hotel.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/4
 */
@Configuration(proxyBeanMethods = false)
public class SpringDocConfig {

    /**
     * 基础配置
     * @return 配置对象
     */
    @Bean
    public OpenAPI openAPI() {
        String websiteUrl = "http://www.xianoupeng.cn";
        return new OpenAPI()
                .info(
                        new Info()
                                .title("酒店管理系统接口测试文档")
                                .description("在开发阶段所使用的接口测试文档，生产阶段建议关闭此功能，以增加项目安全性。")
                                .contact(new Contact().name("Jock").email("ycw@openlab.com").url(websiteUrl))
                                .version("v1.0.0"))
                .externalDocs(new ExternalDocumentation().description("酒店管理系统").url(websiteUrl));
    }

    /**
     * 以下为分组配置
     * group: 设置分组名称
     * pathsToMatch: 设置请求匹配路径，支持通配符
     * packagesToScan: 设置要扫描的包
     * @return 分组配置
     */
    @Bean
    public GroupedOpenApi logApi() {
        return GroupedOpenApi.builder()
                .group("日志接口")
                .pathsToMatch("/log/**, /logs")
                .packagesToScan("com.openlab.hotel.controller")
                .build();
    }
    @Bean
    public GroupedOpenApi hotelApi() {
        return GroupedOpenApi.builder()
                .group("酒店接口")
                .pathsToMatch("/hotel/**", "/roomType/**", "/room/**", "/floor/**")
                .packagesToScan("com.openlab.hotel.controller")
                .build();
    }
    @Bean
    public GroupedOpenApi sysApi() {
        return GroupedOpenApi.builder()
                .group("系统接口")
                .pathsToMatch("/role/**", "/user/**")
                .packagesToScan("com.openlab.hotel.controller")
                .build();
    }
}
