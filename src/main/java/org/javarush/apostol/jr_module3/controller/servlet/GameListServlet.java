package org.javarush.apostol.jr_module3.controller.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

import static org.javarush.apostol.jr_module3.util.WebConstants.GAME_LIST;

@Log4j2
@WebServlet
public class GameListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(GAME_LIST).forward(req, resp);
    }
}
