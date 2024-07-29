package org.javarush.apostol.jr_module3.controller.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.log4j.Log4j2;

@Log4j2
@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Application started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Application stopped");
    }
}
