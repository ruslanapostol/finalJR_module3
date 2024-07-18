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
        log.info("Request received at {}", request.getRemoteAddr());
        chain.doFilter(request, response);
        log.info("Response sent to {}", request.getRemoteAddr());

    }

    @Override
    public void init(FilterConfig filterConfig) {
       log.info("Filter initialized");
    }

    @Override
    public void destroy() {
        log.info("Filter destroyed");
    }
}
