package com.vue.vuebackend;

import com.github.pagehelper.PageHelper;
import org.aopalliance.intercept.Interceptor;
import org.apache.catalina.filters.CorsFilter;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.MultipartConfigElement;
import java.util.Properties;

@MapperScan("com.vue.vuebackend.Mapper")
@SpringBootApplication
public class VueBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(VueBackendApplication.class, args);
    }
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper=new PageHelper();
        Properties properties=new Properties();
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        properties.setProperty("dialect","mysql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
        @Bean
        public MultipartResolver multipartResolver() {
            StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
            return multipartResolver;
        }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //上传的单个文件最大值   KB,MB 这里设置为10MB
        DataSize maxSize = DataSize.ofMegabytes(100);
        DataSize requestMaxSize = DataSize.ofMegabytes(300);
        factory.setMaxFileSize(maxSize);
        /// 设置一次上传文件的总大小
        factory.setMaxRequestSize(requestMaxSize);
        return factory.createMultipartConfig();
    }
}
