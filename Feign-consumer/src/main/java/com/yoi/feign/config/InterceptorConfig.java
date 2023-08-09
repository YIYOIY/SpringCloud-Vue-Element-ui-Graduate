package com.yoi.feign.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-07-28 1:22
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Resource
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
        excludePath.add("/book/**");  //首页书记想查询
        excludePath.add("/select_by_series/**");  //书籍按系列查询
        excludePath.add("/login/**");  //登录
        excludePath.add("/add_user");     //注册
        excludePath.add("/img");  //静态资源
        excludePath.add("/lookup/**");  //书籍详情
        excludePath.add("/series/**");  //系列查询

        registry.addInterceptor(jwtInterceptor) // 注册拦截器
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
    }
}
