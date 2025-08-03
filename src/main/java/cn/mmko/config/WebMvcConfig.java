package cn.mmko.config;

import cn.mmko.interceptor.JwtInterceptor;
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
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/insert",
                        "/user/check",
                        "/product/query",
                        "/product/query/*",
                        "/product/queryBySearch",
                        "/category/query/*",
                        "/images/**",
                        "/product/queryByCategoryId",// 关键：放行静态资源
                        "/order/alipay_notify_url"
                );
    }
}
