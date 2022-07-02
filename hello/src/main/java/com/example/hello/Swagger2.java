package com.example.hello;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * @Descroption:
 * @author: jiangtao
 * @date: Created in 22:03 2021/6/10
 * @Modified By:
 */
//swagger2的配置文件，在项目的启动类的同级文件建立
@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket createRestApi(Environment environment) {
        Profiles profiles = Profiles.of("dev");
        boolean flag = environment.acceptsProfiles(profiles);
        System.out.println(flag);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(flag)
                .select()
// 为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.example.hello.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    // 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
// 页面标题
                .title("Spring Boot 测试使用 Swagger2 构建RESTful API")
// 创建人信息
                .contact(new Contact("liuzhichao", "https://github.com/Liuzhichao99",
                        "7823628@qq.com"))
// 版本号
                .version("1.0")
// 描述
                .description("API 描述")
                .build();
    }
}