package com.example.demo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
public class InterceptorLogger implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(InterceptorLogger.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        StringBuffer requestDetails = new StringBuffer();
        requestDetails.append("preHandle:" + request.getMethod() + request.getRequestURI() +
                " ContentType: " + request.getContentType() + " UserName: "+ userName);

        String requestMethod = request.getMethod();
        if (requestMethod.equalsIgnoreCase("POST") || requestMethod.equalsIgnoreCase("PUT") ){

            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()){
                String element  = (String) parameterNames.nextElement();
                requestDetails.append(" ParamName: "+ element + " Value: "+  request.getParameter(element));
            }
        }

        logger.info(requestDetails.toString());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("[postHandle][" + request + "]");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        Boolean result = true;
        if (ex != null){
            ex.printStackTrace();
            result = false;
        }
        logger.info("afterCompletion result: " + result + " exception: " + ex);
    }
}
