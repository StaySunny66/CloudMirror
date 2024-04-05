package com.gxyos.demo.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /// 这里可以做一些 验证 比如 jwt 的 token 验证 受否有效  /// 用户 提交的数据 是否合法 等等等等

        return false;
    }
}
