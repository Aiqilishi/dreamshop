package cn.mmko.infrastructure.interceptor;

import cn.mmko.infrastructure.utils.JwtUtils;
import cn.mmko.types.enums.ResponseCode;
import cn.mmko.types.exception.AppException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 获取请求头中的 Token
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            // 没有 Token 或格式不对，抛自定义异常，让全局异常处理器处理
            throw new AppException(ResponseCode.UNAUTHORIZED.getCode(), "缺少有效 Token");
        }
        token = token.replace("Bearer ", "");

        // 2. 校验 Token
        if (!JwtUtils.validateToken(token)) {
            throw new AppException(ResponseCode.UNAUTHORIZED.getCode(), "Token 无效或已过期");
        }

        String username = JwtUtils.getUsernameFromToken(token);
        request.setAttribute("username", username);

        return true;
    }
}
