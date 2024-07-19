package org.javarush.apostol.jr_module3.controller.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
@WebFilter("/*")
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("Request received at {} with parameters {}", request.getRemoteAddr(),
                request.getParameterMap());
        chain.doFilter(request, response);
    }
}
