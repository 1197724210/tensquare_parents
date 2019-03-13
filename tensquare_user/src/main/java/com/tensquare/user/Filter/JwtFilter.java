package com.tensquare.user.Filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private HttpServletRequest request;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过拦截器");
        //设置头
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")){
            final String token = header.substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            if(claims != null){
                //如果市管理员
                if("admin".equals(claims.get("roles"))){
                    request.setAttribute("admin_claims",claims);
                }
                //如果是用户
                if("user".equals(claims.get("roles"))){
                    request.setAttribute("user_claims",claims);
                }
            }
        }
        return true;
    }
}
