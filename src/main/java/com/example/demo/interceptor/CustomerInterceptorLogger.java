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
public class CustomerInterceptorLogger implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CustomerInterceptorLogger.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {


        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        StringBuilder requestDetails = new StringBuilder();
        requestDetails.append("Customer preHandle:")
                .append(request.getMethod())
                .append(request.getRequestURI())
                .append(" ContentType: ")
                .append(request.getContentType())
                .append(" UserName: ").append(userName);

        String requestMethod = request.getMethod();
        if (requestMethod.equalsIgnoreCase("POST") || requestMethod.equalsIgnoreCase("PUT") ){

            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()){
                String element  = parameterNames.nextElement();
                requestDetails.append(" ParamName: ")
                        .append(element)
                        .append(" Value: ")
                        .append(request.getParameter(element));
            }
        }

        logger.info(requestDetails.toString());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        logger.info("Customer postHandle response status: " + response.getStatus());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        boolean result = true;
        if (ex != null){
            ex.printStackTrace();
            result = false;
        }
        logger.info("Customer  afterCompletion result: " + result + " exception: " + ex);
    }
}
