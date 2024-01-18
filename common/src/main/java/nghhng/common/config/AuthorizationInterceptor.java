package nghhng.common.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Lấy giá trị từ header Authorization
        String authorizationHeader = request.getHeader("Authorization");

        // Lưu giá trị vào một đối tượng lưu trữ tạm thời (có thể là ThreadLocal hoặc các cơ chế khác)
        AuthorizationContextHolder.setAuthorization(authorizationHeader);

        // Cho phép tiếp tục xử lý request
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Các hành động sau khi xử lý request
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Các hành động sau khi hoàn tất xử lý request
    }
}