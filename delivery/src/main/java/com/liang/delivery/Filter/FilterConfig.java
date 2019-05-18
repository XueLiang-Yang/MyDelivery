package com.liang.delivery.Filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class FilterConfig implements WebMvcConfigurer{

    @Resource
    LoginInterceptor loginInterceptor;
    //拦截器配置
   /* @Override
    public void addInterceptors(InterceptorRegistry registry){
        String[] excludes = new String[]{"/js/**","/css/**","/user/login"};
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(excludes);
    }*/

}
