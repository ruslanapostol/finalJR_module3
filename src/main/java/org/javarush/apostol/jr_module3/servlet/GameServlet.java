package org.javarush.apostol.jr_module3.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.InputStream;
import java.util.Map;
@Log4j2
@WebServlet("/game")
public class GameServlet extends HttpServlet {
    private Map<String, Object> gameLogic;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("game_logic.json")) {
            ObjectMapper mapper = new ObjectMapper();
            gameLogic = mapper.readValue(input, Map.class);
        } catch (IOException e) {
            throw new ServletException("Unable to load game logic", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        GameState gameState = (GameState) session.getAttribute("gameState");
        if (gameState == null) {
            gameState = new GameState();
            session.setAttribute("gameState", gameState);
        }

        String currentStep = gameState.getCurrentStep();
        Map<String, Object> stepData = (Map<String, Object>) gameLogic.get(currentStep);

        request.setAttribute("question", stepData.get("question"));
        request.setAttribute("options", stepData.get("options"));
        request.setAttribute("end", stepData.get("end"));

        request.getRequestDispatcher("/game.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            IOException {
        String answer = request.getParameter("answer");
        HttpSession session = request.getSession();
        GameState gameState = (GameState) session.getAttribute("gameState");

        if (gameState == null) {
            gameState = new GameState();
            session.setAttribute("gameState", gameState);
        }

        Map<String, Object> stepData = (Map<String, Object>) gameLogic.get(gameState.getCurrentStep());
        Map<String, String> options = (Map<String, String>) stepData.get("options");

        if (options != null && options.containsKey(answer)) {
            gameState.setCurrentStep(options.get(answer));
        } else {
            gameState.reset();
        }

        response.sendRedirect("game");
    }
}
