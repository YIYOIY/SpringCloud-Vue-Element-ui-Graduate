package com.yoi.feign.config;

/**
 * @author 游弋
 * @create 2023-07-28 1:22
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private JWTInterceptor jwtInterceptor;

    // 解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")

                // 是否允许cookie
                .allowCredentials(true)

                // 设置允许的请求方式
                .allowedMethods("GET", "POST", "DELETE", "PUT")
//                .allowedMethods("*")

                // 设置允许的header属性
                .allowedHeaders("*")

                // 跨域允许时间
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();
        //这里用来排除拦截的接口,例如登录前调用的接口
        excludePath.add("/book");  //首页
        excludePath.add("/login/**");  //登录
        excludePath.add("/addUser");     //注册
        excludePath.add("/img");  //静态资源
        excludePath.add("/lookup");  //书籍详情
        excludePath.add("/series");  //系列查询
        excludePath.add("/selectBySeries");  //根据系列查书籍

        registry.addInterceptor(jwtInterceptor) // 注册拦截器
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
    }
}
