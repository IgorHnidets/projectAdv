package com.logos.projectadv.config;

import com.logos.projectadv.utills.FileUtills;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class myConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String imagesfolder = FileUtills.getImagesFolder();
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///" + imagesfolder);
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/cabinet").setViewName("cabinet");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/index/home").setViewName("header");
        registry.addViewController("/bucket").setViewName("bucket");
        registry.addViewController("/candidates").setViewName("candidates");
        registry.addViewController("/createproduct").setViewName("createproduct");
    }

}
