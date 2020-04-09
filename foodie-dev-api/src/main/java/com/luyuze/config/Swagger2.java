package com.luyuze.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {
    // http://localhost:8088/swagger-ui.html
    // http://localhost:8088/doc.html
    // 配置Swagger2核心配置 docket
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2) // 指定api类型为SWAGGER_2
                .apiInfo(apiInfo()) // 用于定于api文档汇总信息
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.luyuze.controller")) // 指定controller包
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("java电商平台api")  // 文档页标题
                .contact(new Contact("luyuze",
                        "https://www.imooc.com",
                        "627668587@qq.com"))  // 联系人信息
                .description("为天天吃货提供的api文档")  // 描述信息
                .version("1.0.1") // 文档版本号
                .termsOfServiceUrl("https://www.imooc.com") // 网站地址
                .build();
    }
}
