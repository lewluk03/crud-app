package com.example.demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

@Component
public class SqlInjectionFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(SqlInjectionFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();
        StringBuilder parameters = new StringBuilder();

        Enumeration<String> parameterNames = httpRequest.getParameterNames();
            while (parameterNames.hasMoreElements()){
            String element  = parameterNames.nextElement();
            parameters.append(element);
            parameters.append( httpRequest.getParameter(element));
        }

        boolean notAllowedChar = parameters.toString().matches(".*\\b(>|<|-|)\\b.*");
        logger.info("doFilter path: "+ path +" notAllowedChar: " + notAllowedChar);

        if (notAllowedChar){
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.reset();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ServletResponseWrapper responseWrapper = (ServletResponseWrapper)response;
            responseWrapper.getResponse().resetBuffer();
            String message = "Bad request: request contains illegal characters that suggest SQL injection. Please check everything before sending the request";
            responseWrapper.getResponse().getOutputStream().write(message.getBytes(StandardCharsets.UTF_8));
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
