package cn.mmko.config;

import cn.mmko.infrastructure.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置拦截器，指定拦截路径和排除路径
        registry.addInterceptor(jwtInterceptor)
                // 拦截所有路径，实际根据需求调整，比如拦截 /user/** 下的接口
                .addPathPatterns("/**")
                // 排除登录、注册等不需要校验的接口，根据你的实际路径改
                .excludePathPatterns("/user/insert",
                                     "/user/check");
    }
}
