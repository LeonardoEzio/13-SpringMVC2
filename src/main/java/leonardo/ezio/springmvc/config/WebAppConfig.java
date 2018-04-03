package leonardo.ezio.springmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan(basePackages = "leonardo.ezio.springmvc.controller")
@EnableWebMvc
public class WebAppConfig extends WebMvcConfigurerAdapter{

    public WebAppConfig(){
        System.out.println("WebAppConfig  init....");
    }

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("utf-8");
        multipartResolver.setMaxInMemorySize(1024);
        multipartResolver.setMaxUploadSize(-1);
        return multipartResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/*.js").addResourceLocations("/WEB-INF/js/");
        registry.addResourceHandler("/*.html").addResourceLocations("WEB-INF/html/");
    }
}
