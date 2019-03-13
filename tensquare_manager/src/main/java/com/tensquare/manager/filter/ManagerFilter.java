package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;//过滤器开关
    }

    @Override
    public Object run() throws ZuulException {
       System.out.println("Zuul过滤器");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        if(request.getMethod().equals("OPTIONS")){
            return null;
        }
        String url = request.getRequestURI().toString();
        if(url.indexOf("/admin/login") > 0){
            System.out.println("登录页面" + url);
            return null;
        }
        String header = request.getHeader("Authorization");
         if(header != null && header.startsWith("Bearer ")){
             String token = header.substring(7);
             Claims claims = jwtUtil.parseJWT(token);
             if(claims != null){
                 if("admin".equals(claims.get("roles"))){
                     requestContext.addZuulRequestHeader("Authorization",header);
                     System.out.println("token验证通过，添加了头信息 " + header );
                     return null;
                 }
             }
         }
         requestContext.setSendZuulResponse(false);
         requestContext.setResponseStatusCode(401);
         requestContext.setResponseBody("无权访问");
         return null;
    }
}
