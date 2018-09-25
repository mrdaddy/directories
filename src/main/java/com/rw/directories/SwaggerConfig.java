package com.rw.directories;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration(value = "test1")
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rw.directories.controllers"))
                .paths(regex("/v1/directories.*"))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Сервис операций со справочниками СППД")
                .description("Сервис включает в себя следующую информацию: <ul>" +
                        "<li>− параметры СППД;</li>" +
                        "<li>− список государств выдачи документа, удостоверяющего личность пассажира;</li>" +
                        "<li>− справочник платежных инструментов системы “Расчет“;</li>" +
                        "<li>− справочника государств, в которые могут продаваться проездные документы</li>" +
                        "<li>− список типов документов, удостоверяющих личность пассажира;</li>" +
                        /*"<li>− классы обслуживания;</li>" +
                        "<li>− типы вагонов;</li>" +
                        "<li>− графическое представлениt вагона поезда с нумерованными местами;</li>" +*/
                        "<li>− информацию об обновлениях справочников;</li>"
                        )
                .version("v1")
                .license("БЖД")
                .licenseUrl("https://www.rw.by/")
                .build();
    }

}