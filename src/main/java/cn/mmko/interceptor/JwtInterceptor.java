package cn.mmko.interceptor;

import cn.mmko.utils.JwtUtils;
import cn.mmko.enums.ResponseCode;
import cn.mmko.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 获取请求头中的 Token
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            // 没有 Token 或格式不对，抛自定义异常，让全局异常处理器处理
            log.info("缺少有效 Token{}",token);
            throw new AppException(ResponseCode.UNAUTHORIZED.getCode(), "缺少有效 Token");
        }
        token = token.replace("Bearer ", "");

        // 2. 校验 Token
        if (!JwtUtils.validateToken(token)) {
            throw new AppException(ResponseCode.UNAUTHORIZED.getCode(), "Token 无效或已过期");
        }
        String username = JwtUtils.getUsernameFromToken(token);
        List<String> role = JwtUtils.getRoleFromToken(token);
        List<String> permission = JwtUtils.getPermissionFromToken(token);
        Long userId = JwtUtils.getUserIdFromToken(token);
        Long customerId = JwtUtils.getCustomerIdFromToken(token);
        Long sellerId = JwtUtils.getSellerIdFromToken(token);
        if (customerId != null) request.setAttribute("customerId", customerId);
        if (sellerId != null) request.setAttribute("sellerId", sellerId);
        request.setAttribute("username", username);
        request.setAttribute("role",role);
        request.setAttribute("permission",permission);
        request.setAttribute("userId",userId);
        return true;
    }
}
