package org.javarush.apostol.jr_module3.controller.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.javarush.apostol.jr_module3.model.GameState;
import java.io.IOException;
import static org.javarush.apostol.jr_module3.util.WebConstants.*;

@Log4j2
@WebServlet(EDIT_GAME)
public class EditGameServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) {
        log.info("initialising {}", this.getClass().getSimpleName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/editGame.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        GameState gameState = (GameState) session.getAttribute("gameState");

        if (gameState == null) {
            gameState = new GameState();
        }

        // Update gameState based on form data
        String currentStep = req.getParameter("currentStep");
        if (currentStep != null && !currentStep.isEmpty()) {
            gameState.setCurrentStep(currentStep);
        }

        session.setAttribute("gameState", gameState);
        resp.sendRedirect(GAME);
    }
}
