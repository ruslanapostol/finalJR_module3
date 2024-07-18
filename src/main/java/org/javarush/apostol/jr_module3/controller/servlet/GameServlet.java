package org.javarush.apostol.jr_module3.controller.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
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
import org.javarush.apostol.jr_module3.model.GameStep;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


import static org.javarush.apostol.jr_module3.util.WebConstants.*;
@Log4j2
@WebServlet(GAME)
public class GameServlet extends HttpServlet {
    private Map<String, GameStep> gameLogic;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("game_logic.json")) {
            ObjectMapper mapper = new ObjectMapper();
            gameLogic = mapper.readValue(input, new TypeReference<>() {
            });
        } catch (IOException e) {
            log.error("Unable to load game logic", e);
            throw new ServletException("Unable to load game logic", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        GameState gameState = getOrCreateGameState(session);

        String playerName = request.getParameter("playerName");
        if (playerName != null && !playerName.trim().isEmpty()) {
            session.setAttribute("playerName", playerName);
        }

        GameStep stepData = gameLogic.get(gameState.getCurrentStep());
        setGameStepAttributes(request, stepData);

        request.getRequestDispatcher("/game.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String answer = request.getParameter("answer");
        HttpSession session = request.getSession();
        GameState gameState = getOrCreateGameState(session);

        GameStep stepData = gameLogic.get(gameState.getCurrentStep());
        if (stepData.getOptions() != null && stepData.getOptions().containsKey(answer)) {
            gameState.setCurrentStep(stepData.getOptions().get(answer));
        } else {
            gameState.reset();
        }

        response.sendRedirect(GAME);
    }

    private GameState getOrCreateGameState(HttpSession session) {
        GameState gameState = (GameState) session.getAttribute("gameState");
        if (gameState == null) {
            gameState = new GameState();
            session.setAttribute("gameState", gameState);
        }
        return gameState;
    }

    private static void setGameStepAttributes(HttpServletRequest request, GameStep stepData) {
        request.setAttribute("question", stepData.getQuestion());
        request.setAttribute("options", stepData.getOptions());
        request.setAttribute("end", stepData.getEnd());
    }
}